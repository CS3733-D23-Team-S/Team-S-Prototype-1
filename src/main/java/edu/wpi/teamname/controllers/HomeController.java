package edu.wpi.teamname.controllers;

import static edu.wpi.teamname.navigation.Screen.PATHFINDING;

import edu.wpi.teamname.Main;
import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HomeController {

  @FXML MFXButton mealDeliveryButton;
  @FXML MFXButton reserveRoomButton;
  @FXML MenuButton menuButton;
  @FXML MenuItem exitOption;

  @FXML MFXButton helpButton;

  @FXML MFXButton homeToPathfindingButton;
  @FXML MFXButton signageButton;
  @FXML ImageView conferenceroom;

  @FXML ImageView mealdelivery;

  public void initialize() {
    Image mealImage = new Image(Main.class.getResource("images/Meal_Icon.jpg").toString());
    mealdelivery.setImage(mealImage);

    Image conferenceImage =
        new Image(Main.class.getResource("images/Conference_room_icon.jpg").toString());
    conferenceroom.setImage(conferenceImage);

    // Adding the menu option to exit application

    mealDeliveryButton.setOnMouseClicked(event -> goToMealPage());
    reserveRoomButton.setOnMouseClicked(event -> goToRoomPage());
    signageButton.setOnMouseClicked(event -> goToSignagePage());
    exitOption.setOnAction(event -> exitApplication());
    homeToPathfindingButton.setOnMouseClicked(event -> Navigation.navigate(PATHFINDING));

    // event handler for exiting application

    //    mealDeliveryButton.setOnMouseClicked(event ->
    // Navigation.navigate(Screen.SERVICE_REQUEST));
    //    reserveRoomButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
  }

  public void goToRoomPage() {
    Navigation.navigate(Screen.SERVICE_REQUEST);
  }

  public void goToMealPage() {
    Navigation.navigate(Screen.MEAL_DELIVERY);
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
