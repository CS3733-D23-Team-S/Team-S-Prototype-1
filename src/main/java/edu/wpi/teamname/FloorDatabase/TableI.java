package edu.wpi.teamname.FloorDatabase;

import java.sql.SQLException;
import java.util.List;

public interface TableI
{
    List<Object> getAllRows();

    void updateRow(Object target);

    void deleteRow(Object target);

    Object getRow(Object target);

    void initTable() throws SQLException;

    void resetData() throws SQLException;
}
