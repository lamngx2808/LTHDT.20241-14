package sourcecode.packetrouting.view.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sourcecode.packetrouting.controller.HelpController;

public class HelpScreen {
  Scene scene;
  public HelpScreen() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/helpScreen.fxml"));
      HelpController controller = new HelpController();
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
