/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Runs the actuall ClickBrickBreaker game
 * 
 * For each frame of animation: Update positions and velocities of each ball
 *                              Check if each brick has been hit by a ball
 *                              Update each brick
 * 
 * At the end of each level: Shift each brick down one row
 *                           Add another ball to shoot
 * 
 * How to Play: Click a position on the screen to shoot all your balls
 *              You receive another ball to shoot each level,
 *              except each 3rd level, cause that would be too easy
 * 
 * How to Win: Reach level 50
 * How to Lose: A brick reaches the bottom of the screen before you break it
 *
 * Scores: When you finish the game by losing or winning, you have the option
 *         to add your score to the scoreboard; you're score will be saved for
 *         the next user
 * 
 * Execution: java ClickBrickBreaker
 *  
 */

//Import classes to read and write to scoreboard
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ClickBrickBreaker{
    //Fields
    private static int level = 1;
    private static Level realLevel;
    private static int numBalls = 0;
    private static LinkedList<Brick> brickList = new LinkedList<Brick>();
    private static LinkedList<Ball> ballList = new LinkedList<Ball>();
    private static LinkedList<Score> scoreList = new LinkedList<Score>();
    private static final String SCOREBOARD = "scoreboard.txt";
    

    //Main
    public static void main(String[] args) {
        //Initializes a screen to draw on 
        PennDraw.setCanvasSize(800, 800);
        PennDraw.enableAnimation(50);
        
        boolean hasShot = false;
        boolean gameOver = false;
        //Starts the game
        while(!gameOver) {
            //Updates numBalls
            numBalls = ballList.size() + 1;
            PennDraw.clear();
            
            //Creates level of bricks, each with lives = current level
            realLevel = new Level(level);
            
            //Retrieves the created bricks as an array of bricks
            Brick[] addToLL = realLevel.getBrickArray();
            
            //Adds the created bricks created to the linked list
            for (int i = 0; i < addToLL.length; i++) {
                brickList.add(addToLL[i]);
            }
            drawAllBricks();
            
            //Draws starting ball at bottom of screen
            if (level == 1) {
                Ball first = new Ball(0, 0.5, 0.03);
                first.draw();
            }
            else {
                ballList.get(0).draw();
            }
            drawLevel();
            PennDraw.advance();

            //Waits for user to aim by clicking a position on the screen
            hasShot = false;
            while(!hasShot) {
                if (PennDraw.mousePressed() && PennDraw.mouseY() > 0.2) {
                    hasShot = true;
                    // Gets the coordinates of the mouse cursor
                    double xClick = PennDraw.mouseX();
                    double yClick = PennDraw.mouseY();
                    
                    ballList.add(new Ball(xClick, yClick));
                    
                    //Get position of first ball
                    double xFirst = ballList.get(0).getX();
                    double yFirst = ballList.get(0).getY();
                  
                    //Iterates through ballList aiming every ball at mouseclick
                    for(int i = 0; i < ballList.size(); i++) {
                        ballList.get(i).setInitial(xFirst, yFirst);
                        ballList.get(i).aim(xClick, yClick);
                        double a = ballList.get(0).getvX();
                        double b = ballList.get(0).getvY();
                        
                        //Place all balls in a line extending below screen
                        a = xFirst - i * 4 * a;
                        b = yFirst - i * 4 * b;
                        ballList.get(i).setInitial(a, b);
                        
                    }
                }
            }

            //Keeps the animation going until no balls are left on screen
            boolean ballsOnscreen = true;
            while(ballsOnscreen) {
                PennDraw.clear();
                //Iterates through and updates and draws each ball
                for(int i = 0; i < ballList.size(); i++) {
                    //Checks each ball against each brick for collision
                    for(int j = 0; j < brickList.size(); j++) {
                        brickList.get(j).update(ballList.get(i));
                        brickList.get(j).draw();
                    }
                    ballList.get(i).update();
                    ballList.get(i).draw();
                }
                drawLevel();
                PennDraw.advance();
                
                //Checks if balls are onscreen, ends animation if not
                ballsOnscreen = false;
                for(int i = 0; i < ballList.size(); i++) {
                    if (ballList.get(i).getvX() != 0) {
                        ballsOnscreen = true;
                        break;
                    }
                }
            }
            
            //Shifts the remaining bricks down one
            for(int i = 0; i < brickList.size(); i++) {
                brickList.get(i).shiftDown();
            }
            PennDraw.clear();
            drawAllBricks();
            ballList.get(0).draw();
            drawLevel();
            PennDraw.advance();
            
            //Removes any dead bricks from the brickList
            for(int i = 0; i < brickList.size(); i++) {
                if (brickList.get(i).getLives() <= 0) {
                    brickList.remove(i);
                    i--;
                }
            }
            
            //Ends game if any bricks are at bottom of screen
            for(int i = 0; i < brickList.size(); i++) {
                if (brickList.get(i).getYBottom() <= 0.02) {
                    gameOver = true;
                    PennDraw.clear(PennDraw.RED);
                    PennDraw.setPenRadius(0.1);
                    PennDraw.setPenColor(PennDraw.BLACK);
                    PennDraw.text(0.5, 0.8, "Game Over");
                    PennDraw.advance();
                    break;
                }
            }
            
            if (!gameOver) level++;
            if (level > 50) {
                gameOver = true;
                PennDraw.clear(PennDraw.GREEN);
                PennDraw.setPenRadius(0.1);
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.text(0.5, 0.8, "You Win!!!");
                PennDraw.advance();
            }
        }
        
        //Scoreboard
        
        //Gets the user's name
        PennDraw.text(0.5, 0.5, "To save your score, input your name");
        PennDraw.text(0.5, 0.4, "in the console");
        PennDraw.advance();
        System.out.println("What is your name?");
        String name = Prompter.prompt("> ");
        int score = level;
        //Create a new Score with the player's name and level they reached
        Score newScore = new Score(name, score);
        
        //Populate scoreList with scores from the file
        In inStream = new In("scoreboard.txt");
        int numScores = inStream.readInt();
        for (int i = 0; i < numScores; i++) {
            name = inStream.readString();
            score = inStream.readInt();
            //Adds a new score object to the list at the end
            //Fixes bug of adding 2 heads
            if (i == 0) {
                scoreList.add(new Score(name, score));
            }
            else {
                scoreList.add(i, new Score(name, score));
            }
        }
        //Finds the index where to add the most recent score
        int x = 0;
        int index = 0;
        for (int i = 0; i < scoreList.size(); i++) {
            x = scoreList.get(i).getScore();
            if (level < x) index++;
        }
        scoreList.add(index, newScore); //Adds new score at proper position

        //Updates scoreboard.txt file
        try (BufferedWriter scores = 
             new BufferedWriter(new FileWriter(SCOREBOARD))) {
            
            scores.write(Integer.toString(scoreList.size()));
            scores.newLine();
            
            String a = null;
            int b = 0;
            //Prints new scores to scoreboard.txt
            for (int i = 0; i < scoreList.size(); i++) {
                a = scoreList.get(i).getName();
                b = scoreList.get(i).getScore();
                a += " " + Integer.toString(b);
                scores.write(a);
                scores.newLine();
            }
            
        }
        catch (IOException exception) {
        }
        
        
        
        //Prints the scoreboard
        PennDraw.clear(PennDraw.CYAN);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setFontSize(70);
        PennDraw.text(0.5, 0.9, "Current Top Scores:");
        PennDraw.setFontSize(55);
        String ethan = null;
        for(int i = 0; i < 5; i++) {
            ethan = "";
            ethan = scoreList.get(i).getName() + "               ";
            ethan += Integer.toString(scoreList.get(i).getScore());
            PennDraw.text(0.5, 0.7, ethan);
        } 
        
    }
    
    //Helper methods
    
    //Function: Draws all bricks in the linked list
    private static void drawAllBricks() {
        for (int i = 0; i < brickList.size(); i++) {
            brickList.get(i).draw();
        }
    }
    
    //Function: Prints current number of balls and level on the canvas
    private static void drawLevel() {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setFontSize(40);
        PennDraw.text(0.85, 0.04, "Level: " + level);
        PennDraw.setPenColor(PennDraw.GREEN);
        PennDraw.text(0.18, 0.04, "Total Balls: " + numBalls);
    }
    
    
}