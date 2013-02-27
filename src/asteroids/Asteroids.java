package asteroids;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Asteroids extends JFrame {
  private Timer timer;
  private long lastMove;
  private boolean thrust;
  private double angle;

  private static final double THRUST_PER_SECOND = 150;

  public Asteroids(final IFacade facade, boolean undecorated) {
    super("Asteroids");
    final Set<IShip> ships = initModel(facade);
    final AsteroidsView view = new AsteroidsView(ships, facade);
    if (!undecorated) {
      view.setPreferredSize(new Dimension(1024, 768));
    }
    timer = new Timer(1000 / 30, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        long now = System.currentTimeMillis();
        long millisSinceLastMove = now - lastMove;
        lastMove = now;
        double dt = millisSinceLastMove / 1000.;
        if (thrust) {
          facade.thrust(view.getSelected(), THRUST_PER_SECOND * dt);
        }
        if (angle != 0) {
          facade.turn(view.getSelected(), angle);
        }
        for (IShip ship : ships) {
          facade.move(ship, dt);
        }
        view.repaint();
      }
    });
    this.setFocusTraversalKeysEnabled(false);

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          thrust = true;
          view.setThrust(true);
          break;
        case KeyEvent.VK_LEFT:
          angle = Math.PI / 20;
          break;
        case KeyEvent.VK_RIGHT:
          angle = -Math.PI / 20;
          break;
        case KeyEvent.VK_TAB:
          view.selectNext();
          break;
        case KeyEvent.VK_C:
          view.setShowCollisions(!view.getShowCollisions());
          break;
        case KeyEvent.VK_ESCAPE:
          System.exit(0);
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          thrust = false;
          view.setThrust(false);
          break;
        case KeyEvent.VK_LEFT:
          angle = 0;
          break;
        case KeyEvent.VK_RIGHT:
          angle = 0;
          break;
        }
      }
    });
    this.setUndecorated(undecorated);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.getContentPane().add(view);
    this.pack();
  }

  private Set<IShip> initModel(IFacade facade) {
    Set<IShip> ships = new HashSet<IShip>();
    ships.add(facade.createShip(200, 400, 0, 0, 50, 0));
    ships.add(facade.createShip(700, 400, 0, 0, 50, Math.PI));
    ships.add(facade.createShip(450, 600, 0, 0, 75, -Math.PI / 2));
    return ships;
  }

  public void start() {
    this.setFocusable(true);
    this.setAutoRequestFocus(true);
    this.setVisible(true);
    this.requestFocus();
    lastMove = System.currentTimeMillis();
    timer.start();
  }

  public static void main(String[] args) {
    if (GraphicsEnvironment.isHeadless()) {
      System.out.println("no screen detected");
      return;
    } else {
      // <begin>
      IFacade facade = new asteroids.model.Facade();
      // <end>
      GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice device = env.getDefaultScreenDevice();
      Asteroids asteroids;
      if (device.isFullScreenSupported()) {
        asteroids = new Asteroids(facade, true);
        device.setFullScreenWindow(asteroids);
      } else {
        asteroids = new Asteroids(facade, false);
      }
      asteroids.start();
    }
  }
}
