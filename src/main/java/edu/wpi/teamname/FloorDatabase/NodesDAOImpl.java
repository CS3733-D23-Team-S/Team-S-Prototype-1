package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;

import java.sql.*;
import java.util.*;

public class NodesDAOImpl extends DAOImpl implements DAO_I {

	@Getter
	private HashMap<String, Node> nodes;


	public NodesDAOImpl(Connection c) {
		nodes = new HashMap<>();
		super.c = c;
	}

	public void updateNode(Node target) {
		//Handles the edge updates as well
	}

	public List<Node> getAllNodes() {
		return null;
	}

	public void deleteNode(Node target) {
	}

	public void addNode(Node thisNode) {
		try {
			PreparedStatement preparedStatement =
					c.prepareStatement(
							"INSERT INTO "
									+ floorNodeTableName
									+ " (nodeID ,xCoord ,yCoord , Floor , nodeType, Building, longName, shortName) "
									+ " VALUES (?, ?, ? ,?, ?, ?, ?,?)");
			preparedStatement.setString(1, thisNode.getNodeID());
			preparedStatement.setInt(2, thisNode.getXCoord());
			preparedStatement.setInt(3, thisNode.getYCoord());
			preparedStatement.setInt(4, thisNode.getFloor().ordinal());
			preparedStatement.setInt(5, thisNode.getNodeType().ordinal());
			preparedStatement.setString(6, thisNode.getBuilding());
			preparedStatement.setString(7, thisNode.getLongName());
			preparedStatement.setString(8, thisNode.getShortName());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void constructLocalDataBase() throws SQLException {
		Statement stmt = c.createStatement();
		String listOfNodes = "SELECT * FROM " + floorNodeTableName;
		try {
			ResultSet data = stmt.executeQuery(listOfNodes);
			while (data.next()) {
				String nodeID = data.getString("nodeID");
				int xCoord = data.getInt("xCoord");
				int yCoord = data.getInt("yCoord");
				int floor = data.getInt("Floor");
				int nodeType = data.getInt("nodeType");
				String building = data.getString("Building");
				String longName = data.getString("longName");
				String shortName = data.getString("shortName");
				Node floorNode = new Node(nodeID, xCoord, yCoord, Floor.values()[floor],building, NodeType.values()[nodeType], longName, shortName);
               nodes.put(nodeID,floorNode);
			}
		} catch (SQLException e) {
			System.out.println("Error accessing the remote and constructing the list of nodes");
		}
	}

}