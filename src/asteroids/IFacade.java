package asteroids;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>Your class for representing ships should implement the interface
 * <code>IShip</code>. For example, if your class is named <code>ShipImpl</code>
 * , then the header of that class should look as follows:
 * 
 * <p>
 * <code>class ShipImpl implements IShip { ... }</code>
 * </p>
 * 
 * Consult the <a href=
 * "http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">
 * Java tutorial</a> for more information on interfaces.</li>
 * <li>Connect your class to the user interface by creating a class named
 * <code>Facade</code> that implements <code>IFacade</code>. The header of that
 * class should look as follows:
 * 
 * <p>
 * <code>class Facade implements IFacade { ... }</code>
 * </p>
 * 
 * Each method defined in the interface <code>IFacade</code> must be implemented
 * by the class <code>Facade</code>. For example, the implementation of
 * <code>getX</code> should call a method of the given <code>ship</code> to
 * retrieve its x-coordinate.</li>
 * <li>Modify the code between <code>&ltbegin&gt</code> and
 * <code>&ltend&gt</code> in Asteroids.java: replace
 * <code>new asteroids.model.Facade()</code> with <code>new
 * yourpackage.Facade()</code>.</li>
 * <li>You may assume that only null or objects returned by
 * <code>createShip</code> are passed to <code>getX</code>, <code>getY</code>,
 * <code>move</code>, etc. This means that you can safely cast non-null
 * parameters of type <code>IShip</code> to your own class type.</li>
 * <li>Methods in this interface are allowed to throw only
 * <code>ModelException</code>. No other exception types are allowed. This
 * exception can be thrown only if calling a method of your <code>Ship</code>
 * class with the given parameters would violate a precondition or if the method
 * of your <code>Ship</code> class throws an exception (if so, wrap the
 * exception in a <code>ModelException</code>). ModelException should not be
 * used anywhere outside of your Facade implementation.</li>
 * <li>The rules described above and the documentation described below for each
 * method apply only to the class implementing IFacade. Your class for
 * representing robots should follow the rules described in the assignment.</li>
 * <li>Do not modify the signatures of the methods defined in this interface.
 * You can however add additional methods, as long as these additional methods
 * do not overload the existing ones. Each additional method must be implemented
 * in your class <code>Facade</code>.</li>
 * <li>Your class implementing <code>IFacade</code> should offer a default
 * constructor.</li>
 * </ul>
 */
public interface IFacade {

  /**
   * Create a new ship with a default position, velocity, radius and direction.
   * 
   * Result is a unit circle centered on <code>(0, 0)</code> facing right. Its
   * speed is zero.
   */
  public IShip createShip();

  /**
   * Create a new non-null ship with the given position, velocity, radius and
   * angle (in radians).
   */
  public IShip createShip(double x, double y, double xVelocity, double yVelocity, double radius, double angle);

  /**
   * Return the x-coordinate of <code>ship</code>.
   */
  public double getX(IShip ship);

  /**
   * Return the y-coordinate of <code>ship</code>.
   */
  public double getY(IShip ship);

  /**
   * Return the velocity of <code>ship</code> along the X-axis.
   */
  public double getXVelocity(IShip ship);

  /**
   * Return the velocity of <code>ship</code> along the Y-axis.
   */
  public double getYVelocity(IShip ship);

  /**
   * Return the radius of <code>ship</code>.
   */
  public double getRadius(IShip ship);

  /**
   * Return the direction of <code>ship</code> (in radians).
   */
  public double getDirection(IShip ship);

  /**
   * Update <code>ship</code>'s position, assuming it moves <code>dt</code>
   * seconds at its current velocity.
   */
  public void move(IShip ship, double dt);

  /**
   * Update <code>ship</code>'s velocity based on its current velocity, its
   * direction and the given <code>amount</code>.
   */
  public void thrust(IShip ship, double amount);

  /**
   * Update the direction of <code>ship</code> by adding <code>angle</code> (in
   * radians) to its current direction. <code>angle</code> may be negative.
   */
  public void turn(IShip ship, double angle);

  /**
   * Return the distance between <code>ship1</code> and <code>ship2</code>.
   * 
   * The absolute value of the result of this method is the minimum distance
   * either ship should move such that both ships are adjacent. Note that the
   * result must be negative if the ships overlap. The distance between a ship
   * and itself is 0.
   */
  public double getDistanceBetween(IShip ship1, IShip ship2);

  /**
   * Check whether <code>ship1</code> and <code>ship2</code> overlap. A ship
   * always overlaps with itself.
   */
  public boolean overlap(IShip ship1, IShip ship2);

  /**
   * Return the number of seconds until the first collision between
   * <code>ship1</code> and <code>ship2</code>, or Double.POSITIVE_INFINITY if
   * they never collide. A ship never collides with itself.
   */
  public double getTimeToCollision(IShip ship1, IShip ship2);

  /**
   * Return the first position where <code>ship1</code> and <code>ship2</code>
   * collide, or <code>null</code> if they never collide. A ship never collides
   * with itself.
   * 
   * The result of this method is either null or an array of length 2, where the
   * element at index 0 represents the x-coordinate and the element at index 1
   * represents the y-coordinate.
   */
  public double[] getCollisionPosition(IShip ship1, IShip ship2);
}
