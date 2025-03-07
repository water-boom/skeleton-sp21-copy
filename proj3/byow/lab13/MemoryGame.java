package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        long seed = 114514;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        //TODO: Initialize random number generator
        this.width = width;
        this.height = height;
        this.rand = new Random(seed);

        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0 ; i < n ;  i++){
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width/2,height/2,s);
        if (!gameOver){
            StdDraw.setFont(new Font("Monaco" , Font.BOLD,30));
            StdDraw.textLeft(1,height - 1,"ROUND:" + round);
            StdDraw.textRight(width - 1 , height - 1, playerTurn ? "Type!" : "Watch");
            StdDraw.setFont(new Font("Monaco" , Font.BOLD,30));
        }

        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (char c: letters.toCharArray()){
            drawFrame(String.valueOf(c));
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder sb = new StringBuilder();
        while (sb.length() < n){
            if (StdDraw.hasNextKeyTyped()){
                sb.append(StdDraw.nextKeyTyped());
                drawFrame(sb.toString());
            }
        }
        return sb.toString();
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        round = 1;
        //TODO: Establish Engine loop
        while (!gameOver){
            playerTurn = false;
            drawFrame("ROUND" + round);
            StdDraw.pause(1000);

            String sequence = generateRandomString(round);
            flashSequence(sequence);

            playerTurn = true;
            String playInput = solicitNCharsInput(round);

            if (!playInput.equals(sequence)){
                gameOver = true;
            }else{
                round++;
            }
        }
        drawFrame("游戏结束，最高记录"+round);

    }

}
