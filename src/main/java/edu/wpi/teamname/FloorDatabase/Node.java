package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;
import lombok.Setter;

public class Node {
  @Getter @Setter private String nodeID;
  @Getter @Setter private int xCoord;
  @Getter @Setter private int yCoord;
  @Getter @Setter private Floor floor;
  @Getter @Setter private String building;
  @Getter @Setter private NodeType nodeType;
  @Getter @Setter private String longName;
  @Getter @Setter private String shortName;

  public Node(
      String nodeID,
      int xCoord,
      int yCoord,
      Floor floor,
      String building,
      NodeType nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }

  @Override
  public String toString() {
    return "Node{" + "nodeID='" + nodeID + '\'' + '}';
  }
}
