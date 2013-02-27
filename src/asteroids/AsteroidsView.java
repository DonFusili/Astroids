package asteroids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JPanel;

import static java.lang.Math.*;

@SuppressWarnings("serial")
public class AsteroidsView extends JPanel {

  private Set<IShip> ships;
  private IFacade facade;
  private IShip selected;
  private Iterator<IShip> iterator;
  private boolean thrust = false;
  private boolean showCollisions = false;

  public AsteroidsView(Set<IShip> ships, IFacade facade) {
    super(true);
    this.ships = ships;
    this.iterator = ships.iterator();
    this.selected = iterator.next();
    this.facade = facade;
    this.setBackground(Color.BLACK);
  }

  public void selectNext() {
    if (!iterator.hasNext()) {
      iterator = ships.iterator();
    }
    selected = iterator.next();
  }

  public IShip getSelected() {
    return selected;
  }

  public void setThrust(boolean thrust) {
    this.thrust = thrust;
  }

  public void setShowCollisions(boolean show) {
    this.showCollisions = show;
  }

  public boolean getShowCollisions() {
    return showCollisions;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int height = getHeight();
    for (IShip ship : ships) {
      double radius = facade.getRadius(ship);
      double angle = -facade.getDirection(ship);
      double x = facade.getX(ship);
      double y = height - facade.getY(ship);
      if (ship == selected) {
        g2d.setColor(Color.RED);
      } else {
        g2d.setColor(Color.WHITE);
      }
      g2d.drawOval((int) (x - radius), (int) (y - radius), (int) (2 * radius), (int) (2 * radius));
      g2d.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * radius), (int) (y + sin(angle) * radius));
      if (thrust && ship == selected) {
        Path2D.Double flame = new Path2D.Double();
        double flameAngle = Math.PI / 12;
        flame.moveTo(radius * Math.cos(Math.PI - flameAngle), radius * Math.sin(Math.PI - flameAngle));
        flame.lineTo(-radius - radius / 3, 0);
        flame.lineTo(radius * Math.cos(Math.PI + flameAngle), radius * Math.sin(Math.PI + flameAngle));
        flame.transform(AffineTransform.getRotateInstance(angle));
        flame.transform(AffineTransform.getTranslateInstance(x, y));
        g2d.setColor(Color.orange);
        g2d.draw(flame);
      }
    }
    if (showCollisions) {
      double min_dt = Double.POSITIVE_INFINITY;
      IShip min_ship = null;
      // find next collision, if any
      for (IShip ship : ships) {
        double dt = facade.getTimeToCollision(selected, ship);
        if (dt < min_dt) {
          min_dt = dt;
          min_ship = ship;
        }
      }
      IShip first = min_ship;
      if (first != null && !facade.overlap(selected, first)) {
        // draw circles
        double dt = facade.getTimeToCollision(selected, first);
        int x1_ = (int) (facade.getX(selected) + dt * facade.getXVelocity(selected));
        int y1_ = height - (int) (facade.getY(selected) + dt * facade.getYVelocity(selected));
        int radius1 = (int) facade.getRadius(selected);
        float[] dashPattern = { 10, 5 };
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        g2d.drawOval(x1_ - radius1, y1_ - radius1, 2 * radius1, 2 * radius1);
        g2d.drawLine((int) facade.getX(selected), height - (int) facade.getY(selected), x1_, y1_);
        int x2_ = (int) (facade.getX(first) + dt * facade.getXVelocity(first));
        int y2_ = height - (int) (facade.getY(first) + dt * facade.getYVelocity(first));

        int radius2 = (int) facade.getRadius(first);
        g2d.drawOval(x2_ - radius2, y2_ - radius2, 2 * radius2, 2 * radius2);
        g2d.drawLine((int) facade.getX(first), height - (int) facade.getY(first), x2_, y2_);
      }
      // draw cross
      for (IShip ship : ships) {
        if (!facade.overlap(selected, ship)) {
          double[] colPos = facade.getCollisionPosition(selected, ship);
          if (colPos != null) {
            int x = (int) colPos[0];
            int y = height - (int) colPos[1];
            g2d.setColor(Color.WHITE);
            g2d.drawLine(x - 5, y, x + 5, y);
            g2d.drawLine(x, y - 5, x, y + 5);
          }
        }
      }
    }
  }
}
