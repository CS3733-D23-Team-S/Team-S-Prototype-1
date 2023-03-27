package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.csvConverter;

public class Main {

  public static void main(String[] args) {

    csvConverter converter = new csvConverter();
    System.out.println(converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv"));
    System.out.println(converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv"));
  }

  // shortcut: psvm

}
