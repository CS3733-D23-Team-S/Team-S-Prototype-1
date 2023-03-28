package edu.wpi.teamname.FloorDatabase;

import java.sql.SQLException;
import java.util.List;

public interface NodesDAO {

	List<Node> getAllNodes();

	Node getNeighbors(Node target);

	void updateNode(Node target);

	void deleteNode(Node target);

	void establishConnection();

	void initTable() throws SQLException;

	void resetData() throws SQLException;

	void constructLocalDataBase() throws SQLException;

}
