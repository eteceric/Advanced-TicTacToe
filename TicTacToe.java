import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class TicTacToe {
    
  //  private Spot[][] grid; //must be [9][9]
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean big;

    
    public TicTacToe (int x, int y, int w, int h, boolean big){
       // this.grid = grid;
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        this.big = big;

    }
    
    public static void drawInstructions(Graphics g) throws IOException{

        try {
            BufferedReader r = new BufferedReader(new FileReader("Instructions.txt"));

            int numLine = 0;
            while (r.ready()){
                String print = r.readLine();
                numLine++;
                g.drawString(print, 15, (15 * numLine) );

            }
            r.close();
            
        }
        catch(IOException e){
            throw new FileNotFoundException();
        }
    }
    public static void storeHighScore(TreeMap<Integer, String> scoreMap) throws IOException{

            try{   
                                
                BufferedWriter r = new BufferedWriter(new FileWriter("highscores.txt"));
                int i = 0;
                while (i < 82){
                    if (scoreMap.containsKey(i)){
                        r.write(scoreMap.get(i));
                        r.newLine();

                    }
                    
                    i++;
                }


                r.close();
                }
            catch(IOException c){
                 throw new FileNotFoundException();
         }
    
        } 
        
    public static void readHighScores(Graphics g) throws IOException{

        try{
            int i = 0;
            String s = " ";
            BufferedReader r = new BufferedReader(new FileReader("highscores.txt"));
            while (r.ready()){
                s = r.readLine();
                g.drawString(s, 50, 15 * (17 + i));
                i++;
            }
            r.close();
            
        }
        catch(IOException e){
            throw new FileNotFoundException();
        }
        
    } 
   /* public static void displayScores(Graphics g, int x, int y) throws IOException{
        try{
            g.setColor(Color.darkGray);
            String hs = readHighScores();
            if (!hs.equals(" ")){
                g.drawString(hs, x, y);
                displayScores(g, x, y + 15);
            }
        }
        catch(IOException f){
            throw new FileNotFoundException();
        }
        
    } */
    
    public void draw(Graphics g){

        g.setColor(Color.BLACK);
        if (big){
        g.fillRect(width/3-2, y, 4, width);   // 4 is thickness
        g.fillRect(2*width/3-2, y, 4, width);
        g.fillRect(x, y+width/3-2, width, 4);
        g.fillRect(x, y+2*width/3-2, width, 4);
        }
        else{
        g.drawLine(x+width/3, y, x+width/3, y+height);
        g.drawLine(x+2*width/3, y, x+2*width/3, y+height);
        g.drawLine(x, y+height/3, x+width, y+height/3);
        g.drawLine(x, y+2*height/3, x+width, y+2*height/3); }
   
    }
    
    
}
