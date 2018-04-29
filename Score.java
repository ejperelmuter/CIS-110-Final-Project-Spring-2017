/*  Name: Ethan Perelmuter
 *  PennKey: peethan
 *  Recitation: 211
 *
 *  Class Function: Creates a score object to save the player's score
 *                  Each score saves a name associated with the score
 *  
 *  
 */
public class Score {
    //Fields
    private String name;
    private int points;
    
    //Constructor
    public Score(String name, int points) {
        this.name = name;
        this.points = points;
    }
    
    //Methods
    
    /* Function: Print's the score on the canvas
     * Input: The x and y-coordinate where to print the text on the canvas
     * Output: none
     */
    public void drawScore(double x, double y){
        PennDraw.text(x, y, Integer.toString(points));
    }
    
    /* Function: Retrives the current points of the Score
     * Input: none
     * Output: the current points as an int
     */
    public int getScore() {
        return points;
    }
    
    /* Function: Sets the name
     * Input: The name to set as a string
     * Output: none
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /* Function: Retrieves the name belonging to the Score
     * Input: none
     * Output: The name belonging to this score as a String
     */
    public String getName() {
        return name;
    }
}