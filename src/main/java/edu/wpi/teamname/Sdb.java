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
    dbManager.resetData();
    dbManager.initTables();

    NodesDAOImpl mapDatabase = new NodesDAOImpl(connection);
    EdgesDAOImpl edgeDatabase = new EdgesDAOImpl(connection);
  }
}
