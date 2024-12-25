package PacketRouting.GUI;

import PacketRouting.Components.Network;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class SimulationController {
  Network network;
  @FXML
  private Label testLabel;
  @FXML
  private BorderPane borderPane;
  @FXML
  private VBox leftVBox;

  private Dialog<ButtonType> nodeDialogPane;
  private Dialog<ButtonType> connectionDialogPane;
  private Dialog<ButtonType> packetDialogPane;

  public SimulationController() {
    network = new Network();
  }

  @FXML
  public void handleMouseDragged(MouseEvent event) {
    // Update position of the AnchorPane
    testLabel.setLayoutX(event.getSceneX() - leftVBox.getWidth());
    testLabel.setLayoutY(event.getSceneY());
  }

  @FXML
  public void addNodes(){
    // Show the dialog
    nodeDialogPane.showAndWait();
  }

  private void initNodeDialogPane(){
// Create a DialogPane
    DialogPane dialogPane = new DialogPane();
    dialogPane.setLayoutX(153.0);
    dialogPane.setLayoutY(238.0);

    // Create the header
    Label headerLabel = new Label("Add node dialog");
    headerLabel.setAlignment(javafx.geometry.Pos.CENTER);
    dialogPane.setHeader(headerLabel);

    // Create the content VBox
    VBox contentBox = new VBox();

    // Create Labels and TextFields
    Label nodeTypeLabel = new Label("Node type");

    // Create RadioButtons with ToggleGroup
    ToggleGroup nodeTypeGroup = new ToggleGroup();

    RadioButton routerRadioButton = new RadioButton("Router");
    routerRadioButton.setToggleGroup(nodeTypeGroup);
    routerRadioButton.setSelected(true); // Set Router as selected

    RadioButton switchRadioButton = new RadioButton("Switch");
    switchRadioButton.setToggleGroup(nodeTypeGroup);

    RadioButton computerRadioButton = new RadioButton("Computer");
    computerRadioButton.setToggleGroup(nodeTypeGroup);

    RadioButton hubRadioButton = new RadioButton("Hub");
    hubRadioButton.setToggleGroup(nodeTypeGroup);

    Label nodeNameLabel = new Label("Node name");
    TextField nodeNameField = new TextField();

    Label nodeIpLabel = new Label("Node IP address");
    TextField nodeIpField = new TextField();

    Label nodeMacLabel = new Label("Node MAC address");
    TextField nodeMacField = new TextField();

    // Add all components to the VBox
    contentBox.getChildren().addAll(
            nodeTypeLabel,
            routerRadioButton,
            switchRadioButton,
            computerRadioButton,
            hubRadioButton,
            nodeNameLabel,
            nodeNameField,
            nodeIpLabel,
            nodeIpField,
            nodeMacLabel,
            nodeMacField
    );

    // Set the content of the DialogPane
    dialogPane.setContent(contentBox);

    // Create a Dialog to show the DialogPane
    nodeDialogPane = new Dialog<>();
    nodeDialogPane.setDialogPane(dialogPane);

    // Add buttons to the dialog (optional)
    nodeDialogPane.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);  }
}
