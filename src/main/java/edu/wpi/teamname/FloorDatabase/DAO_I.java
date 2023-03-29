package edu.wpi.teamname.FloorDatabase;

import java.sql.SQLException;

public interface DAO_I {

  void establishConnection();

  void initTables() throws SQLException;

  //  boolean checkTablePresence() throws SQLException;

  void resetData() throws SQLException;

  void constructLocalDataBase() throws SQLException;
}
