package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;
import org.postgresql.jdbc2.ArrayAssistant;

import java.net.ConnectException;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class EdgesDAOImpl extends DAOImpl implements DAO_I {

	@Getter
	private HashMap<String, ArrayList<String>> edges;


	public EdgesDAOImpl(Connection c) {
		super.c = c;
		edges = new HashMap<>();
	}

  public Node getNeighbors(Node target) {
    return null;
  }

  public List<Node> getAllEdges() {
    return null;
  }

  public void deleteEdge(Node target) {}

	public void addEdge(Edge thisEdge) {
		try {
			PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO " + edgesTableName +
													" (startNode, endNode, edgeID) " + " VALUES (?, ?, ? )");
			preparedStatement.setString(1, "'" + thisEdge.getStartNode().getNodeID() + "'");
			preparedStatement.setString(2, "'" + thisEdge.getEndNode().getNodeID() + "'");
			preparedStatement.setString(3, "'" + thisEdge.getEdgeID() + "'");

  public void addEdge(Edge thisEdge) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "INSERT INTO "
                  + edgesTableName
                  + " (startNode, endNode, edgeID) "
                  + " VALUES (?, ?, ? )");
      preparedStatement.setString(1, thisEdge.getStartNode().getNodeID());
      preparedStatement.setString(2, thisEdge.getEndNode().getNodeID());
      preparedStatement.setString(3, thisEdge.getEdgeID());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void constructLocalDataBase() throws SQLException {
		Statement stmt = c.createStatement();
		String getNodes = "SELECT nodeID FROM " + floorNodeTableName;
		PreparedStatement getNeighbors = c.prepareStatement("SELECT * FROM " + edgesTableName + " WHERE startNode = ?");
		try {
			ResultSet listOfNodes = stmt.executeQuery(getNodes);
			while (listOfNodes.next()) {
				String currentNode = listOfNodes.getString("nodeID");
				getNeighbors.setString(1,currentNode);
				ResultSet data = getNeighbors.executeQuery();
				ArrayList<String> neighbors = new ArrayList<>();
				while (data.next()) {
					neighbors.add(data.getString("endNode"));
				}
				edges.put(currentNode,neighbors);
			}
		}catch (SQLException e){
			return;
		}
	}
}
