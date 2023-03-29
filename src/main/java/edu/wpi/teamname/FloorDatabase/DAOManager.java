package edu.wpi.teamname.FloorDatabase;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import lombok.Getter;

public class DAOManager extends DAOImpl implements DAO_I {

  @Getter private HashMap<String, HashSet<String>> edges;
  @Getter private HashMap<String, Node> nodes;

  public DAOManager() {
    edges = new HashMap<>();
    nodes = new HashMap<>();
  }

  @Override
  public void constructLocalDataBase() throws SQLException {
    constructLocalFloorDataBase();
    constructLocalEdgeDataBase();
  }

  public void addNode(Node thisNode) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "INSERT INTO "
                  + floorNodeTableName
                  + " (nodeID ,xCoord ,yCoord , Floor, Building, nodeType, longName, shortName) "
                  + " VALUES (?, ?, ? ,?, ?, ?, ?, ?)");
      preparedStatement.setString(1, thisNode.getNodeID());
      preparedStatement.setInt(2, thisNode.getXCoord());
      preparedStatement.setInt(3, thisNode.getYCoord());
      preparedStatement.setInt(4, thisNode.getFloor().ordinal());
      preparedStatement.setString(5, thisNode.getBuilding());
      preparedStatement.setInt(6, thisNode.getNodeType().ordinal());
      preparedStatement.setString(7, thisNode.getLongName());
      preparedStatement.setString(8, thisNode.getShortName());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
  }

  public void addEdge(Edge thisEdge) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "INSERT INTO "
                  + edgesTableName
                  + " (startNode, endNode, edgeID) "
                  + " VALUES (?, ?, ? )");
      preparedStatement.setString(1, thisEdge.getStartNode().getNodeID());
      preparedStatement.setString(2, thisEdge.getEndNode().getNodeID());
      preparedStatement.setString(3, thisEdge.getEdgeID());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateLocationName(String nodeId, String longName) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement("UPDATE " + floorNodeTableName + " SET longname = ? WHERE nodeID = ?");
      preparedStatement.setString(1, longName);
      preparedStatement.setString(2, nodeId);
      Node temp = nodes.get(nodeId);
      if (temp == null) {
        System.out.println("This node is not in the database");
        return;
      }
      temp.setLongName(longName);
      nodes.put(nodeId, temp);
      preparedStatement.executeUpdate();
      System.out.println("Updated Node");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
      // Handle the exception appropriately
    }
    // Handles the edge updates as well
  }

  public void updateCoord(String nodeId, int xcoord, int ycoord) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "UPDATE " + floorNodeTableName + " SET xcoord = ?, ycoord = ? WHERE nodeID = ?");
      preparedStatement.setInt(1, xcoord);
      preparedStatement.setInt(2, ycoord);
      preparedStatement.setString(3, nodeId);
      Node temp = nodes.get(nodeId);
      if (temp == null) {
        System.out.println("This node is not in the database");
        return;
      }
      temp.setXCoord(xcoord);
      temp.setXCoord(ycoord);
      nodes.put(nodeId, temp);
      preparedStatement.executeUpdate();
      System.out.println("Updated Coordinates");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
      // Handle the exception appropriately
    }
  }

  public void deleteNode(String target) throws SQLException {
    PreparedStatement deleteNode =
        c.prepareStatement("DELETE FROM " + floorNodeTableName + " WHERE nodeID = ?");
    try {
      deleteNode.setString(1, target);
      deleteNode.execute();
      nodes.remove(target);
      edges.remove(target);
      for (String key : edges.keySet()) {
        edges.get(key).remove(target);
        if (edges.get(key).isEmpty()) edges.remove(key);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
  }

  private void constructLocalFloorDataBase() throws SQLException {
    Statement stmt = c.createStatement();
    String listOfNodes = "SELECT * FROM " + floorNodeTableName;
    try {
      ResultSet data = stmt.executeQuery(listOfNodes);
      while (data.next()) {
        String nodeID = data.getString("nodeID");
        int xCoord = data.getInt("xCoord");
        int yCoord = data.getInt("yCoord");
        int floor = data.getInt("Floor");
        int nodeType = data.getInt("nodeType");
        String building = data.getString("Building");
        String longName = data.getString("longName");
        String shortName = data.getString("shortName");
        Node floorNode =
            new Node(
                nodeID,
                xCoord,
                yCoord,
                Floor.values()[floor],
                building,
                NodeType.values()[nodeType],
                longName,
                shortName);
        nodes.put(nodeID, floorNode);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
      System.out.println("Error accessing the remote and constructing the list of nodes");
    }
  }

  private void constructLocalEdgeDataBase() throws SQLException {
    Statement stmt = c.createStatement();
    String getNodes = "SELECT nodeID FROM " + floorNodeTableName;
    PreparedStatement getNeighbors =
        c.prepareStatement(
            "SELECT * FROM " + edgesTableName + " WHERE startNode = ? OR endnode = ?");
    try {
      ResultSet listOfNodes = stmt.executeQuery(getNodes);
      while (listOfNodes.next()) {
        String currentNode = listOfNodes.getString("nodeID");
        getNeighbors.setString(1, currentNode);
        getNeighbors.setString(2, currentNode);
        ResultSet data = getNeighbors.executeQuery();
        HashSet<String> neighbors = new HashSet<>();
        while (data.next()) {
          neighbors.add(data.getString("endNode"));
        }
        edges.put(currentNode, neighbors);
      }
    } catch (SQLException e) {
      System.out.println("Error accessing the remote and constructing the list of edges");
    }
  }

  public void retrieveRow(String target) throws SQLException {
    PreparedStatement statement =
        c.prepareStatement("SELECT nodeID FROM " + floorNodeTableName + " WHERE nodeID = ?");
    try {
      statement.setString(1, target);
      ResultSet data = statement.executeQuery();
      data.next();
      String nodeID = data.getString("nodeID");
      if (nodes.get(nodeID) == null) {
        System.out.println("This node is not in the database, so its row cannot be printed");
        return;
      }
      System.out.println(nodes.get(nodeID).toString());

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
  }

  public void printLocalDatabases() {
    for (String key : nodes.keySet()) {
      System.out.println(nodes.get(key).toString());
    }
    for (String key : edges.keySet()) {
      System.out.print(key + "\t");
      System.out.println(edges.get(key).toString());
    }
  }
}
