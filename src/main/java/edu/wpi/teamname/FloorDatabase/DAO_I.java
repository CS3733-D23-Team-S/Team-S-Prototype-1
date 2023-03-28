package edu.wpi.teamname.FloorDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO_I {


  Connection establishConnection();

  void initTables() throws SQLException;

//  boolean checkTablePresence() throws SQLException;

  void resetData() throws SQLException;

  void constructLocalDataBase() throws SQLException;
}
