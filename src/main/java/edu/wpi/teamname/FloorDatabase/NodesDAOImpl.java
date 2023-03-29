package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class NodesDAOImpl extends DAOImpl implements DAO_I {

  @Getter private HashSet<Node> nodes;

  public NodesDAOImpl(Connection c) {
    HashSet<Node> nodes = new HashSet<>();
    super.c = c;
  }

  public void updateLocationName(String nodeId, String longName) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement("UPDATE hospitaldb.nodes SET longname = ? WHERE nodeID = ?");

      preparedStatement.setString(1, longName);
      preparedStatement.setString(2, nodeId);

      preparedStatement.executeUpdate();
      System.out.println("Updated Node");
    } catch (SQLException e) {
      e.printStackTrace();
      // Handle the exception appropriately
    }
    // Handles the edge updates as well
  }

  public void updateCoord(String nodeId, int xcoord, int ycoord) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement("UPDATE hospitaldb.nodes SET xcoord = ?, ycoord = ? WHERE nodeID  = ?");

      preparedStatement.setInt(1, xcoord);
      preparedStatement.setInt(2, ycoord);
      preparedStatement.setString(3, nodeId);

      preparedStatement.executeUpdate();
      System.out.println("Updated Coordinates");
    } catch (SQLException e) {
      e.printStackTrace();
      // Handle the exception appropriately
    }
  }

  public List<Node> getAllNodes() {
    return null;
  }

  public void deleteNode(Node target) {}

  public void addNode(Node thisNode) {
    try {
      PreparedStatement preparedStatement =
          c.prepareStatement(
              "INSERT INTO "
                  + floorNodeTableName
                  + " (nodeID ,xCoord ,yCoord , Floor ,Building, longName, shortName) "
                  + " VALUES (?, ?, ? ,?, ?, ?, ?)");
      preparedStatement.setString(1, thisNode.getNodeID());
      preparedStatement.setInt(2, thisNode.getXCoord());
      preparedStatement.setInt(3, thisNode.getYCoord());
      preparedStatement.setInt(4, thisNode.getFloor().ordinal());
      preparedStatement.setString(5, thisNode.getBuilding());
      preparedStatement.setString(6, thisNode.getLongName());
      preparedStatement.setString(7, thisNode.getShortName());

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
