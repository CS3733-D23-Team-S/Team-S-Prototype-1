package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.DAOManager;
import edu.wpi.teamname.FloorDatabase.Node;
import java.util.*;

public class AStar {

  DAOManager dbManager;
  HashMap<String, Node> floors;
  HashMap<String, HashSet<String>> edges;

  public AStar(DAOManager manager) {
    dbManager = manager;
    floors = dbManager.getNodes();
    edges = dbManager.getNeighbors();
  }

  private void updateDataBase() {
    floors = dbManager.getNodes();
    edges = dbManager.getNeighbors();
  }

  public List<String> findPath(String s, String e) {
    updateDataBase();
    Node start = dbManager.getNodes().get(s);
    Node end = dbManager.getNodes().get(e);

    HeuristicNode startHNode = new HeuristicNode(start, calculateWeight(start, end));
    final PriorityQueue<HeuristicNode> nodesYetToSearch = new PriorityQueue<>();
    final HashSet<Node> visitedNodes = new HashSet<>();
    final Map<Node, HeuristicNode> gotHereFrom = new HashMap<>();
    nodesYetToSearch.add(startHNode);
    HeuristicNode currentNode;

    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();
      if (currentNode.node == end) {
        if (!visitedNodes.contains(currentNode.node)) {
          return constructShortestPath(currentNode.node, gotHereFrom);
        }
      }
      for (String nodeToSearchID : getNeighbors(currentNode.node)) {
        Node nodeToSearch = floors.get(nodeToSearchID);
        if (!visitedNodes.contains(nodeToSearch)) {
          double weight = calculateWeight(nodeToSearch, end);

          nodesYetToSearch.add(new HeuristicNode(nodeToSearch, weight));
          gotHereFrom.put(nodeToSearch, currentNode);
        }
      }
      visitedNodes.add(currentNode.node);
    }

    return null;
  }

  private HashSet<String> getNeighbors(Node node) {
    return edges.get(node.getNodeID());
  }

  private double calculateWeight(Node start, Node target) {
    return Math.sqrt(
        Math.pow((start.getXCoord() - target.getXCoord()), 2)
            + Math.pow((start.getYCoord() - target.getYCoord()), 2));
  }

  private List<String> constructShortestPath(
      Node currentNode, Map<Node, HeuristicNode> gotHereFrom) {
    final List<String> pathTaken = new LinkedList<>();
    while (gotHereFrom.get(currentNode) != null) {
      pathTaken.add(currentNode.getNodeID());
      currentNode = gotHereFrom.get(currentNode).node;
    }
    pathTaken.add(currentNode.getNodeID());
    Collections.reverse(pathTaken);
    return pathTaken;
  }

  static class HeuristicNode implements Comparator<HeuristicNode> {
    Node node;
    double weight;

    public HeuristicNode(Node node, double weight) {
      this.node = node;
      this.weight = weight;
    }

    @Override
    public int compare(HeuristicNode o1, HeuristicNode o2) {
      return Double.compare(o1.weight, o2.weight);
    }
  }
}
