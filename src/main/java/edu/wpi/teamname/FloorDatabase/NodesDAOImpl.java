package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;

import java.sql.*;
import java.util.*;

public class NodesDAOImpl extends DAOImpl implements DAO_I {

  @Getter
  private HashSet<Node> nodes;


  public NodesDAOImpl(Connection c) {
    HashSet<Node> nodes = new HashSet<>();
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
                  + " (nodeID ,xCoord ,yCoord , Floor ,Building, longName, shortName) "
                  + " VALUES (?, ?, ? ,?, ?, ?, ?)");
      preparedStatement.setString(1, "'" + thisNode.getNodeID() + "'");
      preparedStatement.setInt(2, thisNode.getXCoord());
      preparedStatement.setInt(3, thisNode.getYCoord());
      preparedStatement.setInt(4, thisNode.getFloor().ordinal());
      preparedStatement.setString(5, "'" + thisNode.getBuilding() + "'");
      preparedStatement.setString(6, "'" + thisNode.getLongName() + "'");
      preparedStatement.setString(7, "'" + thisNode.getShortName() + "'");

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
