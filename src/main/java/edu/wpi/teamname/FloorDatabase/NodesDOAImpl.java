package edu.wpi.teamname.FloorDatabase;

import java.util.*;

public class NodesDOAImpl implements NodesDOA {

	HashSet<Node> floorNodes;
	Map<Node, ArrayList<Node>> edges;

	public NodesDOAImpl() {
		HashSet<Node> floorNodes = new HashSet<>();
		Map<Node, ArrayList<Node>> edges = new HashMap<>();
	}

	@Override
	public List<Node> getAllNodes() {
		return null;
	}

	@Override
	public Node getNeighbors(Node target) {
		return null;
	}

	@Override
	public void updateNode(Node target) {

	}

	@Override
	public void deleteNode(Node target) {
	}
}
