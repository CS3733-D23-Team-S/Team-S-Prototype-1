package edu.wpi.teamname.FloorDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class csvConverter {

    HashMap<String, Node> nodes;
    ArrayList<Edge> edges;

    public csvConverter(){
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }
    public HashMap<String, Node> csvToNode() {
        String csvFilePath = "src/L1Nodes.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
//
                Node thisNode = new Node(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2]),
                        Floor.valueOf(fields[3]), fields[4], NodeType.valueOf(fields[5]), fields[6], fields[7]);
                nodes.put(fields[0], thisNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return nodes;
    }

    public ArrayList<Edge> csvToEdges(String csvFilePath) {


        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
//
                Edge thisEdge = new Edge(nodes.get((fields[1])),nodes.get((fields[1])));
                edges.add(thisEdge);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return edges;

    }
}
