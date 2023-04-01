package edu.wpi.teamname.controllers;

import edu.wpi.teamname.RoomBooking;
import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RoomBookingController {

  @FXML MFXButton r1b1;
  @FXML MFXButton r2b2;

  @FXML MFXButton backButton;

  public void bookRoom(ActionEvent e) {
    System.out.println("Room booking functional");
  }

  public void initialize() {
    r1b1.setOnMouseClicked(event -> RoomBooking.setRoomNum("1"));
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
