package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.*;
import java.util.*;

public class BFS {

  DAOManager dbManager;
  HashMap<String, Node> floors;
  HashMap<String, HashSet<String>> edges;

  public BFS(DAOManager manager) {
    dbManager = manager;
    floors = dbManager.getNodes();
    edges = dbManager.getEdges();
  }

  public final List<Node> findPath(Node start, Node end) {

    final Queue<Node> nodesYetToSearch = new LinkedList<>();
    final HashSet<Node> visitedNodes = new HashSet<>();
    final Map<Node, Node> gotHereFrom = new HashMap<>();

    nodesYetToSearch.add(start);
    Node currentNode = start;
    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();
      if (currentNode == end) {
        if (!visitedNodes.contains(currentNode)) {
          return constructShortestPath(currentNode, gotHereFrom);
        }
      }
      for (String nodeToSearchID : getNeighbors(currentNode)) {
        Node nodeToSearch = floors.get(nodeToSearchID);
        if (!visitedNodes.contains(nodeToSearch)) {
          nodesYetToSearch.add(nodeToSearch);
          gotHereFrom.put(nodeToSearch, currentNode);
        }
      }
      visitedNodes.add(currentNode);
    }
    // If target is never found:
    return null;
  }
  private HashSet<String> getNeighbors(Node node){
    return edges.get(node.getNodeID());
  }
  private List<Node> constructShortestPath(Node currentNode, Map<Node, Node> gotHereFrom) {
    final List<Node> pathTaken = new LinkedList<>();
    while (gotHereFrom.get(currentNode) != null) {
      pathTaken.add(currentNode);
      currentNode = gotHereFrom.get(currentNode);
    }
    pathTaken.add(currentNode);
    Collections.reverse(pathTaken);
    return pathTaken;
  }
}
