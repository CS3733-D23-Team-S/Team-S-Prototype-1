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
    Connection connection = dbManager.establishConnection();

    dbManager.initTables();

    NodesDAOImpl mapDatabase = new NodesDAOImpl(connection);
    EdgesDAOImpl edgeDatabase = new EdgesDAOImpl(connection);
    for (Node thisNode : converter.getNodes().values()) {
      mapDatabase.addNode(thisNode);
    }
    for (Edge thisEdge : converter.getEdges()) {
      edgeDatabase.addEdge(thisEdge);
    }

    mapDatabase.updateNode("CLABS002L1", "White House");
  }
}