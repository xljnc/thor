package com.wt.test.thor.test;

import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;

import java.nio.file.Path;

/**
 * @author qiyu
 * @description
 * @date 2023/1/30 23:04
 */
public class TraversalTest {

    private static String matrixNodeId;

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        Path dbPath = Path.of(dir, "/thor-embedded/data");
        DatabaseManagementService dbManagementService = new DatabaseManagementServiceBuilder(dbPath).build();
        Neo4jUtil.registerShutdownHook(dbManagementService);
        GraphDatabaseService graphDb = dbManagementService.database("neo4j");
        createNode(graphDb);
        printNeoFriends(graphDb);
        printMatrixHackers(graphDb);
    }

    public static void createNode(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            // Create matrix node
            Node matrix = tx.createNode();
            matrixNodeId = matrix.getElementId();

            // Create Neo
            Node thomas = tx.createNode();
            thomas.setProperty("name", "Thomas Anderson");
            thomas.setProperty("age", 29);

            // connect Neo/Thomas to the matrix node
            matrix.createRelationshipTo(thomas, RelTypes.NEO_NODE);

            Node trinity = tx.createNode();
            trinity.setProperty("name", "Trinity");
            Relationship rel = thomas.createRelationshipTo(trinity,
                    RelTypes.KNOWS);
            rel.setProperty("age", "3 days");
            Node morpheus = tx.createNode();
            morpheus.setProperty("name", "Morpheus");
            morpheus.setProperty("rank", "Captain");
            morpheus.setProperty("occupation", "Total badass");
            thomas.createRelationshipTo(morpheus, RelTypes.KNOWS);
            rel = morpheus.createRelationshipTo(trinity, RelTypes.KNOWS);
            rel.setProperty("age", "12 years");
            Node cypher = tx.createNode();
            cypher.setProperty("name", "Cypher");
            cypher.setProperty("last name", "Reagan");
            trinity.createRelationshipTo(cypher, RelTypes.KNOWS);
            rel = morpheus.createRelationshipTo(cypher, RelTypes.KNOWS);
            rel.setProperty("disclosure", "public");
            Node smith = tx.createNode();
            smith.setProperty("name", "Agent Smith");
            smith.setProperty("version", "1.0b");
            smith.setProperty("language", "C++");
            rel = cypher.createRelationshipTo(smith, RelTypes.KNOWS);
            rel.setProperty("disclosure", "secret");
            rel.setProperty("age", "6 months");
            Node architect = tx.createNode();
            architect.setProperty("name", "The Architect");
            smith.createRelationshipTo(architect, RelTypes.CODED_BY);
            tx.commit();
        }
    }

    public static void printNeoFriends(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            Node neoNode = getNeoNode(tx);
            int numberOfFriends = 0;
            String output = neoNode.getProperty("name") + "'s friends:\n";
            Traverser friendsTraverser = getFriends(tx, neoNode);
            for (var friendPath : friendsTraverser) {
                output += "At depth " + friendPath.length() + " => "
                        + friendPath.endNode()
                        .getProperty("name") + "\n";
                numberOfFriends++;
            }
            output += "Number of friends found: " + numberOfFriends + "\n";
            System.out.println(output);
        }
    }

    private static Node getNeoNode(Transaction transaction) {
        return transaction.getNodeByElementId(matrixNodeId)
                .getSingleRelationship(RelTypes.NEO_NODE, Direction.OUTGOING)
                .getEndNode();
    }

    private static Traverser getFriends(Transaction transaction, Node person) {
        TraversalDescription td = transaction.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.KNOWS, Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition());
        return td.traverse(person);
    }

    public static void printMatrixHackers(GraphDatabaseService graphDb) {
        try (Transaction tx = graphDb.beginTx()) {
            String output = "Hackers:\n";
            int numberOfHackers = 0;
            Traverser traverser = findHackers(tx, getNeoNode(tx));
            for (var hackerPath : traverser) {
                output += "At depth " + hackerPath.length() + " => "
                        + hackerPath.endNode()
                        .getProperty("name") + "\n";
                numberOfHackers++;
            }
            output += "Number of hackers found: " + numberOfHackers + "\n";
            System.out.println(output);
        }
    }

    private static Traverser findHackers(Transaction transaction, Node startNode) {
        TraversalDescription td = transaction.traversalDescription()
                .breadthFirst()
                .relationships(RelTypes.CODED_BY, Direction.OUTGOING)
                .relationships(RelTypes.KNOWS, Direction.OUTGOING)
                .evaluator(
                        Evaluators.includeWhereLastRelationshipTypeIs(RelTypes.CODED_BY));
        return td.traverse(startNode);
    }
}
