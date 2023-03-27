package edu.wpi.teamname;

import edu.wpi.teamname.FloorDatabase.*;
import org.hibernate.boot.model.process.internal.UserTypeMutabilityPlanAdapter;

import java.sql.*;

public class Sdb {
	private static final String url = "jdbc:postgresql://database.cs.wpi.edu:5432/teamsdb";
	private static final String user = "teams";
	private static final String password = "teams160";
	private static final String floorNodeTableName = "floorTable";
	private static final String edgesTableName = " edgesTable";


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

	public void initTable() throws SQLException {

		Statement stmt = c.createStatement();
		try {
			String floorTableConstruct = "CREATE TABLE IF NOT EXISTS " +  floorNodeTableName + " "+
					"(nodeID Varchar(100)," +
					"xCoord int," +
					"yCoord int," +
					"Floor int," +
					"Building Varchar(100)," +
					"longName Varchar(100)," +
					"shortName Varchar(100),)";

			String edgeTableConstruct = "CREATE TABLE IF NOT EXISTS " + edgesTableName + " " +
					"(startNode Varchar(100)," +
					"endNode int Varchar(100)," +
					"edgeID Varchar(100)";

			stmt.execute(floorTableConstruct);
			stmt.execute(edgeTableConstruct);
			//import and load the csv files
			System.out.println("Loaded the edges and floor nodes into the database");
		} catch (SQLException) {
			System.out.println("Database update/creation error");
		}
	}

	public void resetData() throws SQLException {
		Statement stmt = c.createStatement();
		try {
			String resetCommand = null;
		} catch (SQLException) {
			System.out.println("Database update/creation error");
		}
	}

	public NodesDOAImpl constructLocalDataBase() throws SQLException {
		Statement stmt = c.createStatement();



		return null;
	}

	// shortcut: psvm

}
