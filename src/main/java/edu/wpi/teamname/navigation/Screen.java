package edu.wpi.teamname.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  ROOM_BOOKING("views/RoomBooking.fxml"),
  HELP_PAGE("views/HelpPage.fxml"),
  PATHFINDING("views/Pathfinding.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE_PAGE("views/Signage.fxml"),

  KITCHENFACILITIES_PAGE("views/KitchenFacilitiesPage.fxml"),
  MEAL_DELIVERY("views/MealDelivery.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
