package edu.wpi.teamname.FloorDatabase;

import java.util.*;

public class NodesDOAImpl implements NodesDOA {

  HashSet<FloorNode> floorNodes;
  Map<FloorNode, ArrayList<FloorNode>> edges;

  public NodesDOAImpl() {
    HashSet<FloorNode> floorNodes = new HashSet<>();
    Map<FloorNode, ArrayList<FloorNode>> edges = new HashMap<>();
  }

  @Override
  public List<FloorNode> getAllNodes() {
    return null;
  }

  @Override
  public FloorNode getNeighbors(FloorNode target) {
    return null;
  }

  @Override
  public void updateNode(FloorNode target) {}

  @Override
  public void deleteNode(FloorNode target) {}
}
