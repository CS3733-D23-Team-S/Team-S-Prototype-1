package edu.wpi.teamname.FloorDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import lombok.Getter;

public class NodesDAOImpl extends DAOImpl implements DAO_I {

  @Getter private HashSet<Node> nodes;

  public NodesDAOImpl(Connection c) {
    HashSet<Node> nodes = new HashSet<>();
    super.c = c;
  }

  public void updateNodeLoc(String nodeId, String longName) {
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

  public List<Node> getAllNodes() {
    return null;
  }

  //Deletes given node from database and from local Hash
  public void deleteNode(Node target) {

    String nodeID = target.getNodeID();
    try {
      PreparedStatement prepstat =
          c.prepareStatement("DELETE FROM hospitaldb.nodes WHERE nodeid = ?");

      prepstat.setString(1, nodeID);

      //delete from database
      prepstat.executeUpdate();

      //delete from Hashmap
      System.out.println("Node Deleted");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

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
