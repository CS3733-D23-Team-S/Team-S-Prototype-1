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

  public ArrayList<String> findPath(String s, String e) {
    updateDataBase();
    final PriorityQueue<HeuristicNode> nodesYetToSearch =
        new PriorityQueue<>(10, new HeuristicNode(null, Double.MAX_VALUE));
    final HashSet<Node> visitedNodes = new HashSet<>();
    final Map<Node, Node> gotHereFrom = new HashMap<>();
    Node start = dbManager.getNodes().get(s);
    Node end = dbManager.getNodes().get(e);
    HeuristicNode startHNode = new HeuristicNode(start, calculateWeight(start, end));
    //    System.out.println(startHNode.node + "\t" + startHNode.weight);
    nodesYetToSearch.add(startHNode);
    HeuristicNode currentNode;

    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();
      if (currentNode.node.getNodeID().equals(e)) {
        return constructShortestPath(currentNode.node, gotHereFrom);
      }
      //      dbManager.printLocalDatabases();
      //      System.out.print(currentNode.node.getNodeID());
      for (String nodeToSearchID : edges.get(currentNode.node.getNodeID())) {
        //        System.out.print(nodeToSearchID + "\t");
        Node nodeToSearch = floors.get(nodeToSearchID);
        if (!visitedNodes.contains(nodeToSearch)) {
          double weight = calculateWeight(nodeToSearch, end);
          nodesYetToSearch.add(new HeuristicNode(nodeToSearch, weight));
          gotHereFrom.put(nodeToSearch, currentNode.node);
        }
      }
      visitedNodes.add(currentNode.node);
    }
    return null;
  }

  private double calculateWeight(Node start, Node target) {
    return Math.sqrt(
        Math.pow((start.getXCoord() - target.getXCoord()), 2)
            + Math.pow((start.getYCoord() - target.getYCoord()), 2));
  }

  private ArrayList<String> constructShortestPath(Node currentNode, Map<Node, Node> gotHereFrom) {
    final ArrayList<String> pathTaken = new ArrayList<>();
    while (gotHereFrom.get(currentNode) != null) {
      pathTaken.add(currentNode.getNodeID());
      currentNode = gotHereFrom.get(currentNode);
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
