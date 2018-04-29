/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Creates a ball object that can bounce around the screen
 *                 Each ball has a position and velocity
 * 
 * Interacts with: Brick.java
 * 
 */
public class Ball {
    //Fields
    private double x = 0.5;
    private double y = 0.03;
    private double vx;
    private double vy;
    private double velocity = 0.02;
    private double radius = 0.025;
    
    //Main Testing
    public static void main(String[] args) {
        PennDraw.setCanvasSize(800, 800);  
        PennDraw.enableAnimation(60);
        
        Ball ethan = null;
        
        while (true) {
            PennDraw.clear(PennDraw.WHITE);
            
            if (PennDraw.mousePressed()) {
                // get the coordinates of the mouse cursor
                double xClick = PennDraw.mouseX();
                double yClick = PennDraw.mouseY();
                
                ethan = new Ball(xClick, yClick);
            }
            if (ethan != null) {
                ethan.draw();
                ethan.update();
            }
            PennDraw.advance();
        }
        
        
    }
    
    
    /* Constructor1
     * Constructs a ball with null velocity at given coordinates
     * Int a allows for a distinction between the two constructors
     */
    public Ball(int a, double x, double y) {
        x = this.x;
        y = this.y;
    }
    
    /* Constructor2
     * Constructs a ball aimed at the inputted x and y coordinates
     */
    public Ball(double x, double y) {
        
        double dx = x - this.x;
        double dy = y - this.y;
        double angle = Math.abs(Math.atan(dy / dx));
        
        vx = velocity * Math.cos(angle);
        vy = velocity * Math.sin(angle);
        
        if (dx < 0) vx = -vx;
        
    }
    
    //Methods
    /* Function: Draws the ball at current coordinates
     * Input: none
     * Output: none
     */
    public void draw() {
        PennDraw.setPenColor(PennDraw.BLUE);
        PennDraw.filledCircle(x, y, radius);
    }
    
    /* Function: Sets the velocities such that the ball is aimed
     *           toward the given coordinate
     * Input: A coordinate to aim the ball towards
     * Output: none
     */
    public void aim(double x, double y) {
        double dx = x - this.x;
        double dy = y - this.y;
        double angle = Math.abs(Math.atan(dy / dx));
        
        vx = velocity * Math.cos(angle);
        vy = velocity * Math.sin(angle);
        
        if (dx < 0) vx = -vx;
    }
    
    /* Function: Sets the position at given coordinate
     * Input: x and y position
     * Output: none
     */
    public void setInitial(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    
    //Series of self-explanatory methods to retrieve private fields
    public double getvX() {
        return vx;
    }
    public double getvY() {
        return vy;
    }
    public double getX() {
        return x;
    }
    public double getXRight() {
        return x + radius;
    }
    public double getXLeft() {
        return x - radius;
    }
    
    public double getY() {
        return y;
    }
    public double getYTop() {
        return y + radius;
    }
    public double getYBottom() {
        return y - radius;
    }
    
    public double getRadius() {
        return radius;
    }
    
    /* Function: Bounces the ball vertically, flipping sign of vy
     * Input: none
     * Output: none
     */
    public void bounceY() {
        vy = -vy;
    }
    
    /* Function: Bounces the ball horizontally, flipping sign of vx
     * Input: none
     * Output: none
     */
    public void bounceX() {
        vx = -vx;
    }
    
    /* Function: Updates the position of the ball
     *           Bounces the ball if it hits a wall
     * Input: none
     * Output: none
     */
    public void update() {
        if (hitSide() && y > radius) {
            //Fixes bug of balls stuck on side
            if ((x < 0.5 && vx < 0) || (x > 0.5 && vx > 0)) {
                bounceX();
            }
        }
        if (hitTop()) {
            bounceY();
        }
        //Shifts x and y coordinates one step of velocity
        x += vx;
        y += vy;
        
        if (hitBottom() && vy < 0) {
            vx = 0;
            vy = 0;
        }
    }
    
    //Series of self-explantory methods that determine if the ball
    //       has hit the sides of the screen
    public boolean hitSide() {
        return (x - radius < 0.01 || x + radius > 0.99);
    }
    public boolean hitTop() {
        return (y + radius >= 1);
    }
    public boolean hitBottom() {
        return (y - radius <= 0);
    }
    
    /* Function: Determines if the ball is on the screen
     * Input: none
     * Output: true if the ball is onscreen, false otherwise
     */
    public boolean onScreen() {
        return (x - radius > 0 && x + radius < 1
                    && y + radius < 1
                    && y - radius > 0);
    }
    
}