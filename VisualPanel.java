package PacketRouting.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

import PacketRouting.Components.*;

public class VisualPanel extends JPanel {
  Network graph = new Network();

  public VisualPanel() {
    setLayout(null);

    ImageIcon image = new ImageIcon("router.png");

    // by doing this, we prevent Swing from resizing
    // our nice component
    setLayout(null);

    DraggableComponent mc = new DraggableComponent("","");
    mc.setIcon(image);
    add(mc);    
  }

}