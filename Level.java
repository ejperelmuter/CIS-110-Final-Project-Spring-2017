/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Creates each level by creating a row of bricks at the top
 *                 of the screen
 *                 The higher the level, the more lives each brick has
 *                 
 * 
 * Interacts with: Brick.java
 *  
 */
public class Level {
    //Fields
    private double topRowY = 0.95; //1 - half height of brick
    private double genRate = 8.0; //Number of bricks to generate per row
    private double brickWidth = 1.0 / 10.0;
    private Brick[] brickArray;
    
    //Main
    public static void main(String[] args) {
        Level ethan = new Level(10);
        Brick[] x = ethan.getBrickArray();
        
        for (int i = 0; i < x.length; i++) {
            x[i].draw();
        }
        //PennDraw.advance();
    }
    
    //Constructor: Creates a level of bricks, each with inputted number of lives
    public Level(int level) {
        addBricks(level);
    }
    
    //Methods
    
    /* Function: Creates a row of bricks with the same y-coordinate
     * Input: The life of the bricks created as an int
     * Output: A brick array of the created bricks, in order of creation
     */
    public Brick[] addBricks(int lives) {
        
        //Picks a random number of bricks to generate
        double numBricksGenerated = Math.random() * genRate;
        numBricksGenerated = genRate - numBricksGenerated;
        double centerX = -1 * (brickWidth / 2);
        double centerY = topRowY;
        
        int numBricks = 1 + (int) numBricksGenerated;
        this.brickArray = new Brick[numBricks];
        
        //Randomly places bricks along top row
        for (int i = 0; i < numBricks; i++) {
            centerX += brickWidth;
            while (Math.random() < 0.4) {
                centerX += brickWidth;
            }
            //Makes sure the brick is on the screen
            if (centerX > 1 - brickWidth / 2) {
                centerX = brickWidth / 2;
            }
            //Adds a brick to the array at a random position on top row
            brickArray[i] = new Brick(centerX, centerY, lives);
        }
        
        return this.brickArray;
    }
    
    /* Function: Retrives the Brick array created by addBricks()
     * Input: none
     * Output: The created bricks as an array of bricks (Brick[])
     */
    public Brick[] getBrickArray() {
        return brickArray;
    }
    
}