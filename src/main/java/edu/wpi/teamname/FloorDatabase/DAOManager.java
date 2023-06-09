package edu.wpi.teamname.FloorDatabase;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import lombok.Getter;

public class DAOManager extends DAOImpl implements DAO_I {

  @Getter private HashMap<String, HashSet<String>> neighbors;
  @Getter private HashMap<String, Node> nodes;

  public DAOManager() {
    neighbors = new HashMap<>();
    nodes = new HashMap<>();
  }
  /**
   * Constructs the ORM from the SQL table remote database. It constructs a HashMap<String nodeID,
   * Node node> that represents the name of the node and then the actual node itself which contains
   * all the data and a Hashmap<String nodeID, HashSet<String nodeID>> that represents the name of a
   * node and then the list of nodes that are accessible from that node
   *
   * @throws SQLException error when there is an issue connecting/accessing the database
   */
  @Override
  public void constructLocalDataBase() throws SQLException {
    if (!nodes.isEmpty()) nodes.clear();
    if (!neighbors.isEmpty()) neighbors.clear();
    constructLocalFloorDataBase();
    constructLocalNeighborsDB();
  }

  /** @param thisNode Adds a node to the remote database */
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

  // Left if desired to use, but technically the above method will be faster and not rely on the
  // remote
  /**
   * @param nodeId the name of the node that needs to be updated
   * @param longName the name that is going to replace the longName data contained in the node
   */
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
      temp.setYCoord(ycoord);
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
      neighbors.remove(target);

      for (String key : neighbors.keySet()) {
        neighbors.get(key).remove(target);
        if (neighbors.get(key).isEmpty()) neighbors.remove(key);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getSQLState());
    }
  }

  //	private void addNodeToFloorDB(Node node) {
  //		nodes.put(node.getNodeID(), node);
  //	}

  /**
   * Constructs the HashMap that represents the nodes in the database. See constructLocalDatabase()
   * for more details
   *
   * @throws SQLException on database error
   */
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

  private void constructLocalNeighborsDB() throws SQLException {
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
          neighbors.add(data.getString("startNode"));
        }
        neighbors.remove(currentNode);
        this.neighbors.put(currentNode, neighbors);
      }
    } catch (SQLException e) {
      System.out.println("Error accessing the remote and constructing the list of edges");
    }
  }

  public void retrieveNodeRow(String target) {
    if (nodes.get(target) == null) {
      System.out.println("This node is not in the database, so its row cannot be printed");
      return;
    }
    System.out.println(nodes.get(target).toString());
  }

  public void retrieveEdgeInformation(String target) {
    if (neighbors.get(target) == null) {
      System.out.println("This node is not in the database, so its row cannot be printed");
      return;
    }
    System.out.println("From this starting node, the following edges exist:");
    for (String name : neighbors.get(target)) {
      System.out.println(target + "_" + name);
    }
  }

  public void printLocalDatabases() {
    for (String key : nodes.keySet()) {
      System.out.println(nodes.get(key).toString());
    }
    for (String key : neighbors.keySet()) {
      System.out.print(key + "\t");
      System.out.println(neighbors.get(key).toString());
    }
  }

  public void csvExporterNode(String csvFilePath) {

    try {
      String sql = "SELECT * FROM hospitaldb.nodes";

      Statement statement = c.createStatement();

      ResultSet result = statement.executeQuery(sql);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

      fileWriter.write("nodeID,xcoord,ycoord,floor,building,nodetype,longname,shortname");

      while (result.next()) {
        String nodeid = result.getString("nodeid");
        int xcoord = result.getInt("xcoord");
        int ycoord = result.getInt("ycoord");
        int floor = result.getInt("floor");
        String building = result.getString("building");
        int nodetype = result.getInt("nodetype");
        String longname = result.getString("longname");
        String shortname = result.getString("shortname");

        String line =
            String.format(
                "%s,%d,%d,%d,%s,%d,%s,%s",
                nodeid, xcoord, ycoord, floor, building, nodetype, longname, shortname);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Database error:");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }
  }

  public void csvExporterEdges(String csvFilePath) throws FileNotFoundException {
    {
      PrintWriter writer = new PrintWriter(csvFilePath);
      writer.print("");

      try {
        String sql = "SELECT * FROM hospitaldb.edges";

        Statement statement = c.createStatement();

        ResultSet result = statement.executeQuery(sql);

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

        fileWriter.write("startnode,endnode,edgeid");

        while (result.next()) {
          String startnode = result.getString("startnode");
          String endnode = result.getString("endnode");
          String edgeid = result.getString("edgeid");

          String line = String.format("%s,%s,%s", startnode, endnode, edgeid);

          fileWriter.newLine();
          fileWriter.write(line);
        }

        statement.close();
        fileWriter.close();

      } catch (SQLException e) {
        System.out.println("Database error:");
        e.printStackTrace();
      } catch (IOException e) {
        System.out.println("File IO error:");
        e.printStackTrace();
      }
    }
  }
}
