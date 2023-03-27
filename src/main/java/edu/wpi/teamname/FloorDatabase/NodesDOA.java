package edu.wpi.teamname.FloorDatabase;

import java.util.List;

public interface NodesDOA {

  List<FloorNode> getAllNodes();

  FloorNode getNeighbors(FloorNode target);

  void updateNode(FloorNode target);

  void deleteNode(FloorNode target);
}
