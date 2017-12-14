import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Mark {
    
    private boolean[] wins; 
    //0 1 2
    //3 4 5
    //6 7 8
    private int player;
    
    public Mark(int player){
        wins = new boolean[9];
        for (boolean b2: wins){
            b2 = false;
        }
        this.player = player;
    }
    public int getPlayer(){
        return player;
    }
    
    public void setWin(int b){
        if (b == -1){
            return;
        }
        wins[b] = true;
    }
    
    public boolean hasWon(int b){
        return wins[b];
    }
    
    public void drawTakeBoard(Graphics g, Spot[][] board){
        g.setColor(Color.ORANGE);
        Spot s = board[0][0];
        g.fillRect(s.getX(), s.getY(),
        s.getWidth()*3, s.getHeight()*3); //default
    }
    
    public boolean hasWonGame(){
        for (int i = 0; i < 9; i+=3){
            if (wins[i] && wins[i+1] && wins[i+2]){
                return true;
            }
        }
        for (int i = 0; i < 3; i++){
            if (wins[i] && wins[i+3] && wins[i+6]){
                return true;
            }
        }
        if (wins[0] && wins[4] && wins[8]){
            return true;
        }
        if (wins[2] && wins[4] && wins[6]){
            return true;
        }
        return false;
       /* class winCombinations{
            private boolean rowWin(){
                
            }
        } */

    }
    public void drawWinner(Graphics g){
        g.setColor(Color.CYAN);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.3F);
        g.setFont(newFont);
        
        g.drawString("Player "+ player + " Wins in " + GameCourt.getMoves()
                     + " moves!", 50, 60);
    }
    
    
    
}