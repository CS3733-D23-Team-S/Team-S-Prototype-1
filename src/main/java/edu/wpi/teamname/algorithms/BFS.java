package edu.wpi.teamname.algorithms;

import edu.wpi.teamname.FloorDatabase.*;
import edu.wpi.teamname.controllers.DataBaseController;
import java.util.*;

public class BFS {
  DataBaseController dataBase = new DataBaseController();

  public BFS() {}

  /* A* Pseudocode
  Set the start node and the end node

  from the start node go to the edge table and determine all the neighboring nodes
  get the information from the node and then use its x and y location in order to
  use the heuristic function to determine the most optimal node to go to next

  add the next optimal node to the hash map using the next node as the key
  set the current node to be the selected node
  repeat the process until the end node is selected
  */

  public final List<Node> findPath(Node start, Node end) {

    final Queue<Node> nodesYetToSearch = new LinkedList<>();
    final Map<Node, Boolean> visitedNodes = new HashMap<>();
    final Map<Node, Node> gotHereFrom = new HashMap<>();

    nodesYetToSearch.add(start);
    Node currentNode = start;
    while (nodesYetToSearch.size() != 0) {
      currentNode = nodesYetToSearch.poll();
      if (currentNode == end) {
        if (!visitedNodes.containsKey(currentNode)) {
          return constructShortestPath(currentNode, gotHereFrom);
        }
      }
      for (Node nodeToSearch : dataBase.getNeighbors(currentNode)) {
        if (!visitedNodes.containsKey(nodeToSearch)) {
          nodesYetToSearch.add(nodeToSearch);
          gotHereFrom.put(nodeToSearch, currentNode);
        }
      }
      visitedNodes.put(currentNode, true);
    }
    // If target is never found:
    return null;
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
