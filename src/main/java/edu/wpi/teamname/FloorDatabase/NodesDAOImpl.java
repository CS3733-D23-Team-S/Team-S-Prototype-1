package edu.wpi.teamname.FloorDatabase;

import java.sql.*;
import java.util.*;

public class NodesDAOImpl implements NodesDAO {

  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";
  private static final String floorNodeTableName = "floortable";
  private static final String edgesTableName = "edgestable";

  private static final String schemaName = "hospitaldb";

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
  public void addNode(Node thisNode) throws SQLException {
    String nodeID = thisNode.getNodeID();

    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "INSERT INTO "
                  + schemaName
                  + "."
                  + floorNodeTableName
                  + " (nodeID ,xCoord ,yCoord , Floor ,Building, longName, shortName) "
                  + " VALUES (?, ?, ? ,?, ?, ?, ?)");
      preparedStatement.setString(1, "'" + thisNode.getNodeID() + "'");
      preparedStatement.setInt(2, thisNode.getXCoord());
      preparedStatement.setInt(3, thisNode.getYCoord());
      preparedStatement.setInt(4, thisNode.getFloor().ordinal());
      preparedStatement.setString(5, "'" + thisNode.getBuilding() + "'");
      preparedStatement.setString(6, "'" + thisNode.getLongName() + "'");
      preparedStatement.setString(7, "'" + thisNode.getShortName() + "'");

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
  public void initTables() throws SQLException {
    Statement stmt = c.createStatement();
    String createSchema = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
    String floorTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + schemaName
            + "."
            + floorNodeTableName
            + " "
            + "(nodeID Varchar(100) PRIMARY KEY,"
            + "xCoord int,"
            + "yCoord int,"
            + "Floor int,"
            + "Building Varchar(100),"
            + "longName Varchar(100),"
            + "shortName Varchar(100))";

    String edgeTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + schemaName
            + "."
            + edgesTableName
            + " "
            + "(startNode Varchar(100),"
            + "endNode Varchar(100),"
            + "edgeID Varchar(100))";
    try {
      stmt.execute(createSchema);
      stmt.executeUpdate(floorTableConstruct);
      stmt.executeUpdate(edgeTableConstruct);
      System.out.println("Loaded the edges and floor tables into the database");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      System.out.println("Database update/creation error");
    }
  }

  @Override
  public void resetData() throws SQLException {
    Statement stmt = c.createStatement();
    String resetCommand = "DROP DATABASE IF EXISTS " + schemaName;
    try {
      stmt.executeUpdate(resetCommand);
      System.out.println("Deleted the database");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      System.out.println("Could not reset the database");
    }
  }

  @Override
  public void constructLocalDataBase() throws SQLException {
    Statement stmt = c.createStatement();
  }
}
