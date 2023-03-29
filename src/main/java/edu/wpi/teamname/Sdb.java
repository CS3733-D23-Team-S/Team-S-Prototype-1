package edu.wpi.teamname;

import java.sql.*;
import java.util.Scanner;

public class Sdb {
  private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
  private static final String user = "teams";
  private static final String password = "teams160";

  public static void main(String[] args)
  {
    {
      Connection c;
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

    Scanner sc = new Scanner(System.in);
    UserInputChoices uic = new UserInputChoices();
    while (true) {

        int choice = sc.nextInt();

        switch (choice) {
          case 1:
            uic.displayNodeInformation();
            break;
          case 2:
            uic.displayEdgeInformation();
            break;
          case 3:
            uic.updateNodeCoordinates();
            break;
          case 4:
            uic.updateNameOfLocationNode();
            break;
          case 5:
            uic.exportNodeTableIntoCSV();
            break;
          case 6:
            uic.importFromCSVIntoNodeTable();
            break;
          case 7:
            uic.help();
            break;
          case 0:
            System.exit(0);
            break;
          default:
            break;

        }
    }


    // App.launch(App.class, args);
  }

  // shortcut: psvm

}
