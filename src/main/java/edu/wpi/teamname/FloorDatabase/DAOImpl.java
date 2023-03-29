package edu.wpi.teamname.FloorDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAOImpl {
  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";
  protected static final String schemaName = "hospitaldb";
  protected static final String floorNodeTableName = schemaName + "." + "nodes";
  protected static final String edgesTableName = schemaName + "." + "edges";
  Connection c;

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

  public void initTables() throws SQLException {
    Statement stmt = c.createStatement();

    // This needs to be fixed in the future so that it checks if the data is still present in the
    // table
    resetData();
    //

    String createSchema = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
    String floorTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + floorNodeTableName
            + " (nodeID Varchar(100) UNIQUE PRIMARY KEY,"
            + "xcoord int,"
            + "ycoord int,"
            + "Floor int,"
            + "Building Varchar(100),"
            + "nodeType int,"
            + "longName Varchar(100),"
            + "shortName Varchar(100))";
    String edgeTableConstruct =
        "CREATE TABLE IF NOT EXISTS "
            + edgesTableName
            + " "
            + "(startNode Varchar(100),"
            + "endNode Varchar(100),"
            + "edgeID Varchar(100) UNIQUE )";
    try {
      stmt.execute(createSchema);
      stmt.execute(floorTableConstruct);
      stmt.execute(edgeTableConstruct);
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
}
