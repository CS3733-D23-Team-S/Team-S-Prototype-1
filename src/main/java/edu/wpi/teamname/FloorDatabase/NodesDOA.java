package edu.wpi.teamname.FloorDatabase;

import java.util.List;

public interface NodesDOA {

	List<Node> getAllNodes();

	Node getNeighbors(Node target);

	void updateNode(Node target);

	void deleteNode(Node target);
}
