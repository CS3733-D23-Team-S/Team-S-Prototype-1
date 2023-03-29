package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.DAOManager;
import edu.wpi.teamname.FloorDatabase.Node;
import java.util.*;

public class BFS {

  DAOManager dbManager;
  HashMap<String, Node> floors;
  HashMap<String, HashSet<String>> edges;

  public BFS(DAOManager manager) {
    dbManager = manager;
    floors = dbManager.getNodes();
    edges = dbManager.getNeighbors();
  }

  private void updateDataBase() {
    floors = dbManager.getNodes();
    edges = dbManager.getNeighbors();
  }

  public final ArrayList<String> findPath(String s, String e) {
    updateDataBase();
    Node currentNode = dbManager.getNodes().get(s);
    Node end = dbManager.getNodes().get(e);
    final Queue<Node> nodesYetToSearch = new LinkedList<>();
    final HashSet<Node> visitedNodes = new HashSet<>();
    final Map<Node, Node> gotHereFrom = new HashMap<>();
    nodesYetToSearch.add(currentNode);
    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();
      if (currentNode == end) {
        return constructShortestPath(currentNode, gotHereFrom);
      }
      for (String nodeToSearchID : edges.get(currentNode.getNodeID())) {
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
}
