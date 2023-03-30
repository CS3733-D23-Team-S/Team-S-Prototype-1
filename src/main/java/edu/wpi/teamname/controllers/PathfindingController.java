package edu.wpi.teamname.controllers;

import edu.wpi.teamname.PathfindingEntity;
import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class PathfindingController {
  @FXML MFXButton pathfindingToHomeButton;

  @FXML MFXButton findPathButton;

  @FXML MFXTextField startingLocation;

  @FXML MFXTextField destination;

  public void initialize() {
    pathfindingToHomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    findPathButton.setOnMouseClicked(
        event -> new PathfindingEntity(startingLocation.getText(), destination.getText()));
  }
}
