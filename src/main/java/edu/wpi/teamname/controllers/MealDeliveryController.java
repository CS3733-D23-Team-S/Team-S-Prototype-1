package edu.wpi.teamname.controllers;

import edu.wpi.teamname.MealDeliverySubmission;
import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class MealDeliveryController {

  @FXML MFXButton backButton;
  @FXML MFXButton submitButton;
  @FXML MFXButton friesButton;
  @FXML MFXButton burgersButton;
  @FXML MFXButton soupButton;
  @FXML MFXButton pizzaButton;
  @FXML MFXButton coffeeButton;
  @FXML MFXButton icecreamButton;
  @FXML MFXButton cakeButton;
  @FXML MFXTextField roomNum;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    submitButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    friesButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("fries"));
    burgersButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("burger"));
    soupButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("soup"));
    pizzaButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("pizza"));
    coffeeButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("coffee"));
    icecreamButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("icecream"));
    cakeButton.setOnMouseClicked(event -> MealDeliverySubmission.setFood("cake"));
    roomNum.setOnKeyTyped(event -> MealDeliverySubmission.setRoom(roomNum));
  }
}
