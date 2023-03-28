package edu.wpi.teamname.FloorDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class EdgesDAOImpl extends DAOImpl implements DAO_I {

  @Getter private Map<Node, ArrayList<Node>> edges;

  public EdgesDAOImpl(Connection c) {
    super.c = c;
    Map<Node, ArrayList<Node>> edges = new HashMap<>();
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
}
