package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Sdb {
  public static void main(String[] args) throws SQLException {
    csvConverter converter = new csvConverter();
    converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv");
    converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv");

    for (Edge thisEdge : converter.getEdges()) {
      System.out.println(thisEdge.toString());
    }

    DAOImpl dbManager = new DAOImpl();

    // Establish connection to database
    Connection connection = dbManager.establishConnection();

    // Create Empty Table
    dbManager.initTables();

    NodesDAOImpl mapDatabase = new NodesDAOImpl(connection);
    EdgesDAOImpl edgeDatabase = new EdgesDAOImpl(connection);

    // Inputing Nodes into Data base
    for (Node thisNode : converter.getNodes().values()) {
      mapDatabase.addNode(thisNode);
    }

    // Inputting Edges into Database
    for (Edge thisEdge : converter.getEdges()) {
      edgeDatabase.addEdge(thisEdge);
    }

    //Test Update Node
    //mapDatabase.updateNodeLoc("CLABS002L1", "White House");

    //Test Delete Node ()
    String nodeID = "CLABS002L1";
    //mapDatabase.deleteNode(converter.getNodes().get(nodeID));
  }
}
