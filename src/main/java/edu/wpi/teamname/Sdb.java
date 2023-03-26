package edu.wpi.teamname;

import java.sql.*;

public class Sdb {
  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";

  public static void main(String[] args) {
    {
      Connection c = null;
      try
      {
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection(url, user, password);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
      }
      System.out.println("Opened database successfully");
    }
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
