/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Creates a Brick object that serves as an obstacle to a
 *                 moving ball
 *                 Each brick has a certain number of lives; when a ball
 *                 hits the brick, its life goes down by 1
 *                 When the brick's life is <=0, the brick is no longer drawn
 * 
 * Interacts with: Ball.java
 *  
 */
public class Brick {
    //Fields
    private int lives;
    private double width = 1.0 / 10;
    private double height = 0.1;
    private double centerX;
    private double centerY;
    private double livesText = 0.005;
    
    
    //Main Testing
    public static void main(String[] args) {
        Brick sophie = new Brick(0.5, 0.5, 10);
        Brick ethan = new Brick(0.5, 0.8, 100);
        sophie.draw();
        ethan.draw();
    }
    

    //Constructor: Constructs a brick at given coordinate with given lives
    public Brick(double centerX, double centerY, int lives) {
        ensureValidBrick(centerX, centerY, lives);
        this.centerX = centerX;
        this.centerY = centerY;
        this.lives = lives;
    }
    
    //Constructor2: Constructs a brick with null coordinates with given lives
    public Brick(int lives) {
        this.lives = lives;
    }
    
    //Methods
    
    /* Function: Draws brick with information at current fields
     * Input: none
     * Output: none
     */
    public void draw() {
        if (lives <= 0) return; //Does not draw if brick has no lives
        //Draws brick, the more lives, the darker the brick
        PennDraw.setPenColor(250, 250 - (lives * 5), 0);
        PennDraw.filledRectangle(centerX, centerY, width / 2, height / 2);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setPenRadius(livesText);
        PennDraw.rectangle(centerX, centerY, width / 2, height / 2);
        
        //Draws number of lives in the middle of the brick
        PennDraw.setFontSize(15);
        PennDraw.text(centerX, centerY, Integer.toString(lives));
        
    }
    
    /* Function: If the brick has been hit, decrease its life by 1
     * Input: a Ball to check if it is touching the brick
     * Output: 
     */
    public void update(Ball ball) {
        //Does not update if brick has no lives
        if (lives <= 0) return;
        if (collisionBall(ball)) {
            incrementLives(-1);
        }
    }
    
    /* Function: Set the center coordinates of the Brick
     * Input: x and y coordinates for the center of the Brick
     * Output: 
     */
    public void setCoordinates(double x, double y) {
        centerX = x;
        centerY = y;
    }
    
    /* Function: Get the y-coordinate of the bottom of the brick
     * Input: none
     * Output: The y-coordinate bottom of the brick as a double
     */
    public double getYBottom() {
        return centerY - height / 2;
    }
    
    /* Function: Shifts the brick down by one brick; bottom becomes top
     * Input: none
     * Output: none
     */
    public void shiftDown() {
        centerY = centerY - height;
    }
    
    /* Function: Increases/decreases life by inputted amount
     * Input: Amount to increase/decrease life by as an int
     * Output: 
     */
    public void incrementLives(int increment) {
        lives += increment;
    }
    
    /* Function: Gets the life of the brick
     * Input: none
     * Output: Current life of the brick as an int
     */
    public int getLives() {
        return lives;
    }

    
    /* Function: Determines if the brick has been hit by given ball
     * Input: none
     * Output: True if given ball is touching the brick, false otherwise
     */
    public boolean collisionBall(Ball ball) {
        if (lives <= 0) return false;
        return (collisionBottomTop(ball) || collisionSide(ball));
    }
    
    /* Function: Help method to make sure brick is valid; error checking
     * Input: none
     * Output: none
     */
    private void ensureValidBrick(double x, double y, int lives) {
        if (!(x + width / 2 <= 1 && x - width / 2 >= 0)) {
            throw new IllegalArgumentException("Invalid x coordinate");
        }
        if (!(y + height / 2 <= 1 && y - height / 2 >= 0)) {
            throw new IllegalArgumentException("Invalid y coordinate");
        }
        if (lives <= 0 || lives > 50) {
            throw new IllegalArgumentException("Lives must be between 0 and 50");
        }
    }
    
    //HELPER METHODS    
    //Helper Method to determine if brick has been hit on top or bottom
    private boolean collisionBottomTop(Ball ball) {
        if (lives <= 0) return false;
        
        //Bottom Collision
        if (ball.getYTop() >= centerY - height / 2 &&
            ball.getYTop() <= centerY + height / 2 &&
            ball.getX() >= centerX - width / 2 &&
            ball.getX() <= centerX + width / 2) {
            ball.bounceY();
            return true;
        }
        
        //Top Collision
        else if (ball.getYBottom() <= centerY + height / 2 &&
                 ball.getYBottom() >= centerY - height / 2 &&
                 ball.getX() >= centerX - width / 2 &&
                 ball.getX() <= centerX + width / 2) {
            ball.bounceY();
            return true;
        }
        
        return false;
    }

    //Helper Method to determine if brick has been hit on either side
    private boolean collisionSide(Ball ball) {
        if (lives <= 0) return false;
            
        //Left Side
        if (ball.getXRight() >= centerX - width / 2 &&
            ball.getXRight() <= centerX + width / 2 &&
            ball.getY() >= centerY - height / 2 &&
            ball.getY() <= centerY + height / 2) {
            ball.bounceX();
            return true;
        }
        
        //Right side
        if (ball.getXLeft() <= centerX + width / 2 &&
            ball.getXLeft() >= centerX - width / 2 &&
            ball.getY() >= centerY - height / 2 &&
            ball.getY() <= centerY + height / 2) {
            ball.bounceX(); //Reverses x velocity of ball
            return true;
        }
        return false;
    }
    
    
}