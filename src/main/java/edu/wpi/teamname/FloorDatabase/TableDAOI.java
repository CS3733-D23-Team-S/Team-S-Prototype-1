package edu.wpi.teamname.FloorDatabase;

import java.sql.SQLException;
import java.util.List;

public interface TableDAOI
{
    List<Object> getAllRows();

    void updateRow(Object target);

    void deleteRow(Object target);

    void initTable() throws SQLException;

    void resetData() throws SQLException;
}
