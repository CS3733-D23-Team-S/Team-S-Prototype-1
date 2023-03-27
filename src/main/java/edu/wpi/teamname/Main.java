package edu.wpi.teamname;

import java.sql.*;

public class Main {

  public static void main(String[] args) {
    Sdb database = new Sdb();
    database.establishConnection();

    App.launch(App.class, args);
  }

  // shortcut: psvm

}
