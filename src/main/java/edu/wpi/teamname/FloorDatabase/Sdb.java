package edu.wpi.teamname.FloorDatabase;

public class Sdb {
  public static void main(String[] args) {
    csvConverter converter = new csvConverter();
    converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv");
    converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv");
    for (Node thisNode : converter.nodes.values()) {
      System.out.println(thisNode.getLongName());
    }
  }
}
