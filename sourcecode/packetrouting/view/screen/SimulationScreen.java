package sourcecode.packetrouting.view.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sourcecode.packetrouting.controller.SimulationController;

public class SimulationScreen {
  Scene scene;
  public SimulationScreen() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/simulationScreen.fxml"));
      SimulationController controller = new SimulationController();
      fxmlLoader.setController(controller);
      Parent root = fxmlLoader.load();
      scene = new Scene(root);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Scene getScreen() {
    return scene;
  }
}
