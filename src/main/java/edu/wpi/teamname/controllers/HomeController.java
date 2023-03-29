package edu.wpi.teamname.controllers;

import static edu.wpi.teamname.navigation.Screen.PATHFINDING;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class HomeController {

  @FXML MFXButton mealDeliveryButton;
  @FXML MFXButton reserveRoomButton;

  @FXML MFXButton signageButton;

  @FXML MenuButton menuButton;
  @FXML MenuItem exitOption;

  @FXML MFXButton helpButton;

  @FXML MFXButton homeToPathfinding;

  @FXML
  public void initialize() {
    // Adding the menu option to exit application

    mealDeliveryButton.setOnMouseClicked(event -> goToMealPage());
    reserveRoomButton.setOnMouseClicked(event -> goToRoomPage());
    signageButton.setOnMouseClicked(event -> goToSignagePage());
    exitOption.setOnAction(event -> exitApplication());
    homeToPathfinding.setOnMouseClicked(event -> Navigation.navigate(PATHFINDING));

    // event handler for exiting application

    //    mealDeliveryButton.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST));
    //    reserveRoomButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
  }

  public void goToRoomPage() {
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }

  public void goToMealPage() {
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }

  public void goToSignagePage() {
    Navigation.navigate(Screen.SIGNAGE_PAGE);
  }

  public void exitApplication() {
    Platform.exit();
  }

  public void goToHelpPage() {
    Navigation.navigate(Screen.HELP_PAGE);
  }
}
