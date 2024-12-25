package sourcecode.packetrouting.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sourcecode.packetrouting.controller.MainController;

public class Main extends Application {
  private static Scene scene;
  public static void main(String[] args) {
    System.out.println("Main stage");
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        Platform.exit();
        System.exit(0);
      }
    });
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
      MainController controller = new MainController(stage);
      fxmlLoader.setController(controller);
      Parent root = fxmlLoader.load();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Scene getScene(){
    return scene;
  }
}
