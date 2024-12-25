package PacketRouting.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainController {
  HelpScreen helpScreen = new HelpScreen();
  SimulationScreen simulationScreen = new SimulationScreen();
  static Stage stage;
  public MainController(Stage stage) {
    this.stage = stage;

  }
  @FXML
  public void enterHelpScene(ActionEvent event) {
    stage.setScene(helpScreen.getScreen());
  }

  @FXML
  public void enterSimulationScene(ActionEvent event) {
    stage.setScene(simulationScreen.getScreen());
  }

  @FXML
  public void quitProgram(ActionEvent event) {
    Platform.exit();
  }

  public static void backToMainScene(){
    stage.setScene(Main.getScene());
  }
}
