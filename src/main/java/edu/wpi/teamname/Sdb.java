package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.*;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class Sdb {
  public static void main(String[] args) throws SQLException {
    csvConverter converter = new csvConverter();
    converter.csvToNode("src/main/java/edu/wpi/teamname/L1Nodes.csv");
    converter.csvToEdges("src/main/java/edu/wpi/teamname/L1Edges.csv");

    //    for (Edge thisEdge : converter.getEdges()) {
    //      System.out.println(thisEdge.toString());
    //    }

    DAOManager dbManager = new DAOManager();
    // Establish connection to database
    dbManager.establishConnection();

    // Create Empty Table
    dbManager.initTables();

    // Inputing Nodes into Data base
    for (Node thisNode : converter.getNodes().values()) {
      dbManager.addNode(thisNode);
    }

    // Inputting Edges into Database
    for (Edge thisEdge : converter.getEdges()) {
      dbManager.addEdge(thisEdge);
    }
    dbManager.constructLocalDataBase();
    //    dbManager.printLocalDatabases();
    dbManager.updateLocationName("CLABS002L1", "White House");
    dbManager.updateCoord("CLABS002L1", 200, 300);
    dbManager.retrieveRow("CLABS002L1");
    dbManager.deleteNode("CSERV001L2");

    //    dbManager.printLocalDatabases();

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

  public static void help() {
    System.out.println("HELP------------");
    System.out.println("1. To Display node information press 1, then enter the nodeID");
    System.out.println("2. To Display edge information press 2, then enter the edgeID");
    System.out.println(
        "3. To Update a  node's long name press 3, then enter target node, then enter new name");
    System.out.println("4. To Import from CSV, press 4 then enter file location");
    System.out.println("5. To Export to CSV, press 5. You'll receive location of your CSV file");
    System.out.println("6. To exit, press 7");
  }
}
