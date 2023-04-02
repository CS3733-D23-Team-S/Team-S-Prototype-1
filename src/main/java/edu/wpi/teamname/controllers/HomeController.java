package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  @FXML MFXButton navigateButton;
  @FXML MFXButton mealdelivery1Button;

  @FXML
  public void initialize() {
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    mealdelivery1Button.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_DELIVERY1));
  }
}
