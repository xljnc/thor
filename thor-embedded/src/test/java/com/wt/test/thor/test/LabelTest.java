package com.wt.test.thor.test;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
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
    }

    public static void createLabel(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            tx.createNode(NodeLabel.Person).setProperty("name", "qiyu");
            tx.createNode(NodeLabel.Movie).setProperty("name", "Sleepless In Seattle");
            tx.commit();
        }
    }

    public static void findNodeByLabel(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            tx.findNodes(NodeLabel.Person).stream().forEach(
                    node -> System.out.println(node.getProperty("name"))
            );
            tx.findNodes(NodeLabel.Movie).stream().forEach(
                    node -> {
                        System.out.println(node.getProperty("name"));
                        node.delete();
                    }
            );

        }
    }
}
