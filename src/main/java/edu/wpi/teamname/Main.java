package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.csvConverter;

public class Main {

  public static void main(String[] args) {

    csvConverter converter = new csvConverter();
    System.out.println(converter.csvToNode());
    System.out.println(converter.csvToEdges("src/L1Edges"));
  }

  // shortcut: psvm

}
