package edu.wpi.teamname.FloorDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOImpl {
  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";
  protected static final String schemaName = "hospitaldb";
  protected static final String floorNodeTableName = schemaName + "." + "nodes";
  protected static final String edgesTableName = schemaName + "." + "edges";
  Connection c;

  public Connection establishConnection() {
    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection(url, user, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    return c;
  }

  public void initTables() throws SQLException {
    Statement stmt = c.createStatement();
    String createSchema = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
    String floorTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + floorNodeTableName
            + " (nodeID Varchar(100) PRIMARY KEY,"
            + "xcoord int,"
            + "ycoord int,"
            + "Floor int,"
            + "Building Varchar(100),"
            + "nodeType int,"
            + "longName Varchar(100),"
            + "shortName Varchar(100),"
            + "CONSTRAINT UNIQUE (nodeID))";
    String edgeTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + edgesTableName
            + " "
            + "(startNode Varchar(100),"
            + "endNode Varchar(100),"
            + "edgeID Varchar(100),"
            + "CONSTRAINT UNIQUE (edgeID))";
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

  public void resetData() throws SQLException {
    Statement stmt = c.createStatement();
    String resetCommand = "DROP SCHEMA IF EXISTS " + schemaName + " CASCADE";
    try {
      stmt.executeUpdate(resetCommand);
      System.out.println("Deleted the database");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getSQLState());
      System.out.println("Could not reset the database");
    }
  }

  public void constructLocalDataBase() throws SQLException {}
}
