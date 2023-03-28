package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.Edge;
import edu.wpi.teamname.FloorDatabase.NodesDAOImpl;
import edu.wpi.teamname.FloorDatabase.csvConverter;

import java.sql.SQLException;

public class Sdb {
  public static void main(String[] args) throws SQLException {
    csvConverter converter = new csvConverter();
    converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv");
    converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv");

    for (Edge thisEdge : converter.getEdges()) {
      System.out.println(thisEdge.toString());
    }

    NodesDAOImpl mapDatabase = new NodesDAOImpl();
    mapDatabase.establishConnection();
    mapDatabase.initTable();

  }
}
