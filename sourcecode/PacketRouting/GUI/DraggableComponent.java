package PacketGUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class DraggableComponent extends JLabel {

  private volatile int screenX = 0;
  private volatile int screenY = 0;
  private volatile int myX = 0;
  private volatile int myY = 0;
  String name;
  String ipAddress;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIp(String ip) {
    this.ipAddress = ip;
  }

  public DraggableComponent(String name, String ip) {
    this.name = name;
    this.ipAddress = ip;
    setBounds(0, 0, 64, 41);
    setIcon(new ImageIcon("router.png"));

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        screenX = e.getXOnScreen();
        screenY = e.getYOnScreen();

        myX = getX();
        myY = getY();
      }

    });
    addMouseMotionListener(new MouseMotionAdapter() {

      @Override
      public void mouseDragged(MouseEvent e) {
        int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;

        setLocation(myX + deltaX, myY + deltaY);
        repaint();
      }
    });
  }

}
