package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.Node;

import java.util.*;

public class AStar {

	public AStar(){};

	public List<Node> findPath(Node start, Node end){
		final Queue<Node> nodesYetToSearch = new LinkedList<Node>();
		final Map<Node, Boolean> visitedNodes = new HashMap<Node, Boolean>();
		final Map<Node, Node> gotHereFrom = new HashMap<Node, Node>();


	}

	
	private int calcHeuristicVal(Node start, Node target){

	}



	private List<Node> constructShortestPath(Node currentNode, Map<Node, Node> gotHereFrom) {
		final List<Node> pathTaken = new LinkedList<Node>();
		while (gotHereFrom.get(currentNode) != null) {
			pathTaken.add(currentNode);
			currentNode = gotHereFrom.get(currentNode);
		}
		pathTaken.add(currentNode);
		Collections.reverse(pathTaken);
		return pathTaken;

	}


}
