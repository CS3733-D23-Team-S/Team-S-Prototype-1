package edu.wpi.teamname.FloorDatabase;

import java.sql.*;
import java.util.*;

public class NodesDAOImpl implements NodesDAO {

  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";
  private static final String floorNodeTableName = "floorTable";
  private static final String edgesTableName = " edgesTable";

  Connection c;

  HashSet<Node> nodes;
  Map<Node, ArrayList<Node>> edges;

  public NodesDAOImpl() {
    HashSet<Node> nodes = new HashSet<>();
    Map<Node, ArrayList<Node>> edges = new HashMap<>();
  }

  @Override
  public List<Node> getAllNodes() {
    return null;
  }

  @Override
  public void addNode(Node thisNode) {
    try {
      //      + " "
      //              + "(nodeID Varchar(100),"
      //              + "xCoord int,"
      //              + "yCoord int,"
      //              + "Floor int,"
      //              + "Building Varchar(100),"
      //              + "longName Varchar(100),"
      //              + "shortName Varchar(100))";

      PreparedStatement preparedStatement =
          c.prepareStatement("INSERT INTO floorTable VALUES (?, ?, ? ,?, ?, ?, ?)");
      preparedStatement.setString(1, thisNode.getNodeID());
      preparedStatement.setString(2, String.valueOf(thisNode.getXCoord()));
      preparedStatement.setString(3, String.valueOf(thisNode.getYCoord()));
      preparedStatement.setString(4, String.valueOf(thisNode.getFloor()));
      preparedStatement.setString(5, thisNode.getBuilding());
      preparedStatement.setString(6, thisNode.getLongName());
      preparedStatement.setString(7, thisNode.getShortName());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Node getNeighbors(Node target) {
    return null;
  }

  @Override
  public void updateNode(Node target) {}

  @Override
  public void deleteNode(Node target) {}

  @Override
  public void establishConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
  }

  @Override
  public void initTable() throws SQLException {

    Statement stmt = c.createStatement();
    try {
      String floorTableConstruct =
          "CREATE TABLE IF NOT EXISTS "
              + floorNodeTableName
              + " "
              + "(nodeID Varchar(100),"
              + "xCoord int,"
              + "yCoord int,"
              + "Floor int,"
              + "Building Varchar(100),"
              + "longName Varchar(100),"
              + "shortName Varchar(100))";

      String edgeTableConstruct =
          "CREATE TABLE IF NOT EXISTS "
              + edgesTableName
              + " "
              + "(startNode Varchar(100),"
              + "endNode Varchar(100),"
              + "edgeID Varchar(100))";

      stmt.execute(floorTableConstruct);
      stmt.execute(edgeTableConstruct);
      // import and load the csv files
      System.out.println("Loaded the edges and floor nodes into the database");
    } catch (SQLException e) {
      System.out.println("Database update/creation error");
    }
  }

  @Override
  public void resetData() throws SQLException {
    Statement stmt = c.createStatement();
    try {
      String resetCommand = null;
    } catch (Exception e) {
      System.out.println("Database update/creation error");
    }
  }

  @Override
  public void constructLocalDataBase() throws SQLException {
    Statement stmt = c.createStatement();
  }
}
