package com.wt.test.thor.test;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.Schema;

import java.nio.file.Path;

/**
 * @author qiyu
 * @date 2023/1/28 16:43
 */
public class IndexTest {

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        Path dbPath = Path.of(dir, "/thor-embedded/data");
        DatabaseManagementService dbManagementService = new DatabaseManagementServiceBuilder(dbPath).build();
        Neo4jUtil.registerShutdownHook(dbManagementService);
        GraphDatabaseService graphDb = dbManagementService.database("neo4j");
        createIndex(graphDb);
        createNode(graphDb);
        findNodeById(graphDb);
        updateNodeProperty(graphDb);
        dropNode(graphDb);
        dropIndex(graphDb);
    }

    public static void createIndex(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Schema schema = tx.schema();
            schema.indexFor(Label.label("User"))
                    .on("username")
                    .withName("usernames")
                    .create();
            tx.commit();
        }
    }

    public static void createNode(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Label label = Label.label("User");
            // Create some users
            for (int id = 0; id < 100; id++) {
                Node userNode = tx.createNode(label);
                userNode.setProperty("username", "user" + id + "@neo4j.org");
            }
            System.out.println("Users created");
            tx.commit();
        }
    }

    public static void findNodeById(GraphDatabaseService graphDb) {
        Label label = Label.label("User");
        int idToFind = 45;
        String nameToFind = "user" + idToFind + "@neo4j.org";
        try (Transaction tx = graphDb.beginTx()) {
            for (Node node : tx.findNodes(label, "username", nameToFind).stream().toList()) {
                System.out.println("The username of user " + idToFind + " is " + node.getProperty("username"));
            }
        }
    }

    public static void updateNodeProperty(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Label label = Label.label("User");
            int idToFind = 45;
            String nameToFind = "user" + idToFind + "@neo4j.org";
            for (Node node : tx.findNodes(label, "username", nameToFind).stream().toList()) {
                node.setProperty("username", "user" + (idToFind + 1) + "@neo4j.org");
            }
            tx.commit();
        }
    }

    public static void dropNode(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Label label = Label.label("User");
            int idToFind = 46;
            String nameToFind = "user" + idToFind + "@neo4j.org";
            for (Node node : tx.findNodes(label, "username", nameToFind).stream().toList()) {
                node.delete();
            }
            tx.commit();
        }

    }

    public static void dropIndex(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Schema schema = tx.schema();
            schema.getIndexByName("usernames").drop();
            tx.commit();
        }

    }
}
