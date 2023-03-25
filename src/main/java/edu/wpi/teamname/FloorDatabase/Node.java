package edu.wpi.teamname.FloorDatabase;

import lombok.Getter;
import lombok.Setter;

public class Node
{
    @Getter @Setter
    private int xCord;
    @Getter @Setter
    private int yCord;
    @Getter @Setter
    private Floor floor;
    @Getter @Setter
    private String building;
    @Getter @Setter
    private NodeType nodeType;
    @Getter @Setter
    private String longName;
    @Getter @Setter
    private String shortName;
}
