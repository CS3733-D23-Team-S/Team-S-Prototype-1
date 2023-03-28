package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.Node;
import edu.wpi.teamname.controllers.DataBaseController;
import java.util.*;

public class AStar {
  DataBaseController dataBase = new DataBaseController();

  public AStar() {};

  public List<Node> findPath(Node start, Node end) {
    HeuristicNode startHNode = new HeuristicNode(start, calculateWeight(start, end));
    final PriorityQueue<HeuristicNode> nodesYetToSearch = new PriorityQueue<>();
    final Map<HeuristicNode, Boolean> visitedNodes = new HashMap<>();
    final Map<Node, HeuristicNode> gotHereFrom = new HashMap<>();

    nodesYetToSearch.add(startHNode);
    // Node currentNode = start;
    HeuristicNode currentNode;

    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();

      for (Node nodeToSearch : dataBase.getNeighbors(currentNode.node)) {

        if (!visitedNodes.containsKey(nodeToSearch)) {
          double weight = calculateWeight(nodeToSearch, end);

          nodesYetToSearch.add(new HeuristicNode(nodeToSearch, weight));
          gotHereFrom.put(nodeToSearch, currentNode);
        }
      }
      visitedNodes.put(currentNode, true);
    }

    return null;
  }

  private double calculateWeight(Node start, Node target) {
    double weight =
        Math.sqrt(
            Math.pow((start.getXCoord() - target.getXCoord()), 2) +
            Math.pow((start.getYCoord() - target.getYCoord()), 2));
    return weight;
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

  class HeuristicNode implements Comparator<HeuristicNode> {
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
