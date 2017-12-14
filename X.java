import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class X extends Mark{
    
    
    public X(int player){
        super(player);
    }
    
    @Override
    public void drawTakeBoard(Graphics g, Spot[][] board){
        g.setColor(Color.BLUE);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.4F + 100);
        g.setFont(newFont);
        Spot s = board[2][0];
        g.drawString("X", s.getX() + s.getWidth()/2, s.getY() + (int) (s.getHeight()/2.2) );
        g.setFont(currentFont);
    }
}