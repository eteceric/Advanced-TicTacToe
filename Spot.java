import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
@SuppressWarnings("serial")
public class Spot extends JButton {
    
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private int state; //0 = spot is blank, 1 = spot is player 1's, 2 = spot is player 2's
    private boolean valid; //if or if not this spot can be accessed
    private boolean done;
    private boolean draw;
    
    public Spot (int xPos, int yPos, int w, int h){
        this.xPos = xPos;
        this.yPos = yPos;
        width = w;
        height = h;
        state = 0;
        valid = true;
        done = false;
        draw = false;
    }
    public boolean isDraw(){
        return draw;
    }
    public void setDraw(boolean x){
        draw = x;
    }
    public boolean isMarked(){
        return (state != 0);
    }
    public boolean isValid(){
        return valid;
    }
    public void setValid(boolean x){
        valid = x;
    }
    public void setDone(boolean x){
        done = x;
    }
    public boolean isDone(){
        return done;
    }
    
    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getState(){
        return state;
    }
    public void setState(int player){
        state = player;
    }
    public void drawX(Graphics g){
        g.setColor(Color.BLUE);
        g.drawLine(this.getX()+4, this.getY()+4,
                   this.getX() + this.getWidth()-4,
                   this.getY() + this.getHeight()-4);
        g.drawLine(this.getX() + this.getWidth()-4, this.getY()+4,
                   this.getX()+4,
                   this.getY() + this.getHeight()-4);
    }
    public void drawO(Graphics g){
        g.setColor(Color.RED);
        g.drawOval(this.getX()+3, this.getY()+3,
                  this.getWidth()-6, this.getHeight()-6);
    }
    

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        if (isValid() && !isDone()){
            g.setColor(Color.YELLOW);
        }
        if (isDraw()){
            g.setColor(Color.GRAY);
            g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            return;
        }
        g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if (state == 1){
            drawX(g);
        }
        else if (state == 2){
            drawO(g);
        }
    }
    public boolean isEquals(Spot s){
        return (s.getX() == this.getX() && s.getY() == this.getY());
    }

    
}