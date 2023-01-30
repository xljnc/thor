package com.wt.test.thor.test;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.nio.file.Path;

/**
 * @author qiyu
 * @description
 * @date 2023/1/29 17:00
 */
public class LabelTest {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        Path dbPath = Path.of(dir, "/thor-embedded/data");
        DatabaseManagementService dbManagementService = new DatabaseManagementServiceBuilder(dbPath).build();
        Neo4jUtil.registerShutdownHook(dbManagementService);
        GraphDatabaseService graphDb = dbManagementService.database("neo4j");
        createLabel(graphDb);
        findNodeByLabel(graphDb);
        deleteRelation(graphDb);
        deleteNodeByLabel(graphDb);
    }

    public static void createLabel(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Node tom = tx.createNode(NodeLabel.Person);
            tom.setProperty("name", "tom hanks");
            Node sis = tx.createNode(NodeLabel.Movie);
            sis.setProperty("name", "Sleepless In Seattle");
            tom.createRelationshipTo(sis, RelTypes.ACTED_IN).setProperty("role", "father");
            tx.commit();
        }
    }

    public static void findNodeByLabel(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            tx.findNodes(NodeLabel.Person).stream().forEach(
                    node -> System.out.println(node.getProperty("name"))
            );
            tx.findNodes(NodeLabel.Movie).stream().forEach(
                    node -> System.out.println(node.getProperty("name"))
            );
            tx.commit();
        }
    }

    public static void deleteRelation(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Node tom = tx.findNode(NodeLabel.Person, "name", "tom hanks");
            tom.getSingleRelationship(RelTypes.ACTED_IN, Direction.OUTGOING).delete();
            tx.commit();
        }
    }

    public static void deleteNodeByLabel(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            tx.findNodes(NodeLabel.Person).stream().forEach(
                    node -> node.delete()
            );
            tx.findNodes(NodeLabel.Movie).stream().forEach(
                    node -> node.delete()
            );
            tx.commit();
        }
    }


}
