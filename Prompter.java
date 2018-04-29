/* Name: Ethan Perelmuter
 * PennKey: peethan
 * Recitation: 211
 *
 * Class Function: Provides a way for the player to input their name at
 *                 the end of the game, to save their score
 *  
 */

import java.util.Scanner;

public class Prompter {
    
    static Scanner scanner = new Scanner(System.in);
    
    /* Function: Provides a space for the user to respond with their name
     * Input: The user's name
     * Output: 
     */
    public static String prompt(String prefix) {
        System.out.print(prefix);
        String answer = scanner.nextLine();
        System.out.println();
        System.out.println("Great job, " + answer + 
                           "! I hope you enjoyed the game!");
        return answer;
    }
    
}
