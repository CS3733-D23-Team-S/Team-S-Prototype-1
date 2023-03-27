package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;
import lombok.Setter;

public class Edge {
  @Getter @Setter private FloorNode startNode;
  @Getter @Setter private FloorNode endNode;
  @Getter @Setter private String edgeID;

  public Edge(FloorNode sN, FloorNode eN) {
    startNode = sN;
    endNode = eN;
    edgeID = sN.getNodeID() + "_" + eN.getNodeID();
  }
}
