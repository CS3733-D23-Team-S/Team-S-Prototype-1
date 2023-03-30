package edu.wpi.teamname.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RoomBookingController {

  @FXML MFXButton r1b1;
  @FXML MFXButton r2b2;

  public void bookRoom(ActionEvent e) {
    System.out.println("Room booking functional");
  }

  public void initialize() {}
}
