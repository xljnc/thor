package com.wt.test.thor.test;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.nio.file.Path;

/**
 * @author qiyu
 * @date 2023/1/26 21:54
 */
public class FirstTest {

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        Path dbPath = Path.of(dir, "/thor-embedded/data");
        DatabaseManagementService dbManagementService = new DatabaseManagementServiceBuilder(dbPath).build();
        GraphDatabaseService dbService = dbManagementService.database("neo4j");
        doSomething(dbService);
        registerShutdownHook(dbManagementService);
    }

    public static void doSomething(GraphDatabaseService dbService) {
        try (Transaction tx = dbService.beginTx()) {
            Node firstNode = tx.createNode();
            firstNode.setProperty("message", "Hello, ");
            Node secondNode = tx.createNode();
            secondNode.setProperty("message", "World!");
            Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            relationship.setProperty("message", "brave Neo4j ");
            System.out.print( firstNode.getProperty( "message" ) );
            System.out.print( relationship.getProperty( "message" ) );
            System.out.print( secondNode.getProperty( "message" ) );
            //删节点前必须先删除关系，否则事务提交时会失败
            relationship.delete();
            firstNode.delete();
            secondNode.delete();
            tx.commit();
        }
    }

    /**
     * Registers a shutdown hook for the Neo4j instance so that it
     * shuts down nicely when the VM exits (even if you "Ctrl-C" the
     * running application).
     **/
    private static void registerShutdownHook(final DatabaseManagementService managementService) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> managementService.shutdown()));
    }
}
