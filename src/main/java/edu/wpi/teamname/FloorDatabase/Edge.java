package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;
import lombok.Setter;

public class Edge
{
    @Getter @Setter
    private Node startNode;
    @Getter @Setter
    private Node endNode;

    public Edge(Node startNode, Node endNode){
        this.startNode = startNode;
        this.endNode = endNode;
    }
}
