package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.Edge;
import edu.wpi.teamname.FloorDatabase.csvConverter;

public class Sdb {
  public static void main(String[] args) {
    csvConverter converter = new csvConverter();
    converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv");
    converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv");
    for (Edge thisEdge : converter.getEdges()) {
      System.out.println(thisEdge.toString());
    }
  }
}
