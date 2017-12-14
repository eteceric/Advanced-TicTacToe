

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static java.lang.Math.*;
import java.io.*;
/**
 * GameCourt
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
  
    private int player; //either player 1 or player 2
    private Mark p1; //player 1
    private Mark p2; //player 2
    private ArrayList<Spot> spots; //for the actionlistener
    private ArrayList<TicTacToe> grids; //just for drawing
    private ArrayList<Spot[][]> boards; //to determine when boards are won
                                        //boards will be 
                                        //boards.get(0) 1 2
                                                    //3 4 5
                                                    //6 7 8
    private Spot[][] bigBoard; 
    private int p1wins;
    private int p2wins;
    private int draws;
    private static int moves; //keep track of how many moves made in a game
    private boolean displayInstructions;
    private TreeMap<Integer, String> scoreMap;

    
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 360;
    public static final int COURT_HEIGHT = 440;
    public static final int SQUARE_VELOCITY = 4;
   
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) throws IOException{
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        player = 1;
        p1 = new X(1);
        p2 = new O(2);
        p1wins = 0;
        p2wins = 0;
        draws = 0;
        moves = 0;
        try{
            scoreMap = getScores(scoreMap);
        }
        catch(IOException e){}
        displayInstructions = true;
        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        spots = new ArrayList<Spot>(0);
        for (int i = 0; i < COURT_WIDTH; i+=COURT_WIDTH/9){
            for (int j = 0; j < COURT_WIDTH; j+=COURT_WIDTH/9){
                Spot s = new Spot(i, 80+j, COURT_WIDTH/9, COURT_WIDTH/9);
                spots.add(s);
            }
        }
        
        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                for (Spot s : spots){
                    if (isWithin(e.getPoint(), s)){
                        if (!s.isMarked() && s.isValid() && !s.isDone()){
                            s.setState(player);
                            moves++;
                            int numBoard = fromBoard(s);
                            Spot[][] whichBoard = boards.get(numBoard);
                            int w = boardWinner(whichBoard);
                            int nextBoard = -1;
                            if (w == -1){
                                for (int i = 0; i < 3; i++){
                                    for (int j = 0; j < 3; j++){
                                        whichBoard[i][j].setDone(true);
                                        whichBoard[i][j].setDraw(true); //draw = grayed out
                                    }
                                }

                            }
                            if (w == 1){
                                p1.setWin(numBoard);
                                for (int i = 0; i < 3; i++){
                                    for (int j = 0; j < 3; j++){
                                        whichBoard[i][j].setDone(true); // makes the board "done" so cant be accessed
                                    }
                                }
                               

                            }

                            if (w == 2){
                                p2.setWin(numBoard);
                                for (int i = 0; i < 3; i++){
                                    for (int j = 0; j < 3; j++){
                                        whichBoard[i][j].setDone(true);
                                    }
                                }

                            }
                            for (int i = 0; i <3; i++){
                                for (int j = 0; j < 3; j++){
                                    if (s.isEquals(whichBoard[i][j])){
                                        nextBoard = 3*i+j; //the next board that can be accessed
                                    }
                                }
                            }

                            if (boards.get(nextBoard)[0][0].isDone()){
                                for (Spot s4: spots){
                                    s4.setValid(true);   
                                    }
                                repaint();
                                player = 3 - player;
                                break;  
                            }
                            for (int i = 0; i < boards.size(); i++){
                                if (i != nextBoard){
                                    for (Spot[] u: boards.get(i)){
                                        for (Spot s3: u){
                                            s3.setValid(false);
                                        }
                                    }
                                }
                                else if (i == nextBoard){
                                    for (Spot[] u: boards.get(i)){
                                        for (Spot s3: u){
                                            s3.setValid(true);
                                        }
                                        
                                    }
                                }
                            }
                            repaint();
                            player = 3 - player; //switch from 1 to 2 or from 2 to 1                            
                        }
                        break;
                    }
                }
                
            }
        });


        this.status = status;
    }
    public static int getMoves(){
        return moves;
    }
    public TreeMap getScores(TreeMap<Integer, String> map) throws IOException{
        map = new TreeMap<Integer, String>();
        try{
        BufferedReader r = new BufferedReader(new FileReader("src/highscores.txt"));
        
        while (r.ready()){
            int mves = 0;
            String s = r.readLine();
            if (s.charAt(36) == 32){ //if single digits
                mves = s.charAt(35) - 48; //ascii to int
            }
            else{
                mves = (s.charAt(35) - 48) * 10 + (s.charAt(36) - 48);
            }
            map.put(mves, s);
        }
            r.close();
        }
        catch(IOException e){
            throw new FileNotFoundException();
        }
        return map;
    }
    public int fromBoard(Spot s){
        for (int g = 0; g < boards.size(); g++){
            for (Spot[] x : boards.get(g)){
                for (Spot s2 : x){
                    if (s.isEquals(s2)){
                        return g;
                    }
                }
            }
        }
        return -1;
    }
    public boolean isWithin(Point pos, Spot s){
        return (s.getX() < pos.getX() && s.getX()+s.getWidth() > pos.getX() 
           && s.getY() < pos.getY() && s.getY() + s.getHeight() > pos.getY()) ; 
    }
    
    public int boardWinner(Spot[][] b){

        for (int i = 0; i < 3; i++){
            if (b[i][0].getState() == b[i][1].getState()
                && b[i][2].getState() == b[i][0].getState()
                && b[i][0].getState() == player){
                return player;
            }
        }
        for (int i = 0; i < 3; i++){
            if (b[0][i].getState() == b[1][i].getState()
                && b[2][i].getState() == b[0][i].getState()
                && b[0][i].getState() == player){
                return player;
            }            
        }
        if (b[0][0].getState() == b[1][1].getState()
            && b[2][2].getState() == b[0][0].getState()){
            return b[0][0].getState();
        }
        if (b[0][2].getState() == b[1][1].getState()
            && b[2][0].getState() == b[0][2].getState()){
            return b[1][1].getState();
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (b[i][j].getState() == 0){
                    return 0; //0 = unfinished
                }
            }
        }
        return -1; // -1 = draw 

    }

    public void setInstructions(boolean x){
        displayInstructions = x;
    }
    
    public void startNewGame(){
        displayInstructions = !displayInstructions;

        p2wins = 0;
        p1wins = 0;
        draws = 0;
        rematch();

    }
     
    public boolean areInstructionsDisplayed(){
        return displayInstructions;
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
         if (p1.hasWonGame()){
            p1.drawWinner(g);
            p1wins++;
            try{
                scoreMap = getScores(scoreMap);
		player = 3 - player;
                scoreMap.put(getMoves(), "Player "+player+" - won against Player "+(3-player)+" in "+
                        getMoves()+" moves!");
                TicTacToe.storeHighScore(scoreMap);
            }
            catch(IOException f){
                
            }
            return;
        }
        if (p2.hasWonGame()){
            p2.drawWinner(g);
            p2wins++;
            try{
                scoreMap = getScores(scoreMap);
		player = 3 - player;
                scoreMap.put(getMoves(), "Player "+player+" - won against Player "+(3-player)+" in "+
                        getMoves()+" moves!");
                TicTacToe.storeHighScore(scoreMap);
            }
            catch(IOException f){
                
            }
            return;
        }
        if (displayInstructions){
            try{            
                g.setColor(Color.BLACK);
                TicTacToe.drawInstructions(g);
                
                g.setColor(Color.darkGray);
                g.drawString("HIGH SCORES", 120, 15*15);
                TicTacToe.readHighScores(g);
            }
            catch(IOException f){
                
            }
            return;
        }

        boolean draw = true;
        g.drawString("Moves: " + moves, 270, 60);
        for (Spot s : spots){
            s.draw(g);
        }
        for (TicTacToe t : grids){
            t.draw(g);
        }
        for (int i = 0; i < 9; i++){
            if (p1.hasWon(i)){
                p1.drawTakeBoard(g, boards.get(i));
                
            }
            if (p2.hasWon(i)){
                p2.drawTakeBoard(g, boards.get(i));
            }
        }
        

        for (Spot s: spots){
            if (!s.isDraw() && !s.isDone()){
                draw = false;
            }
        }
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.3F);
        g.setFont(newFont);
        if (draw){
            g.setColor(Color.BLACK);
            g.drawString("It's a DRAW!", 50, 60);
            draws++;
            return;
        }
        g.setColor(Color.BLUE);
        g.drawString("Player " + 1 + "'s wins = " + p1wins, 10, 25);
        g.setColor(Color.RED);
        g.drawString("Player " + 2 + "'s wins = " + p2wins, 190, 25);
        g.setColor(Color.magenta);
        
        g.drawString("Player " + player +"'s turn", 10, 60);
        g.setColor(Color.darkGray);
        g.drawString("Draws: " + draws, 150, 60);

        
    }
    
    public void rematch() { //difference between rematch and reset = rematch doesn't reset the wins
        player = 0;
        p1 = new X(1);
        p2 = new O(2);   
        moves = 0;

        grids = new ArrayList<TicTacToe>(0);
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){ //80 is the diff between courtwidth and height
                TicTacToe t = new TicTacToe(i*COURT_WIDTH/3+5, j*COURT_WIDTH/3+80+5,
                                             COURT_WIDTH/3-10, COURT_WIDTH/3-10, false);
                grids.add(t);
            }
        }
        grids.add(new TicTacToe(0, 80, COURT_WIDTH, 
                                COURT_WIDTH, true));

        spots = new ArrayList<Spot>(0);
        for (int i = 0; i < COURT_WIDTH; i+=COURT_WIDTH/9){
            for (int j = 0; j < COURT_WIDTH; j+=COURT_WIDTH/9){
                Spot s = new Spot(j, 80+i, COURT_WIDTH/9, COURT_WIDTH/9);
                spots.add(s);
            }
        }
        bigBoard = new Spot[9][9];
        int spotNum = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                bigBoard[i][j] = spots.get(spotNum); //[row][column]
                spotNum++;
            }
        }
        boards = new ArrayList<Spot[][]>();
        for (int q = 0; q < 9; q+=3){ //putting all the tictactoe boards into ArrayList boards
            for (int r = 0; r < 9; r+=3){
                Spot[][] b = new Spot[3][3];
                for (int i = q; i < q+3; i++){
                    for (int j = r; j < r+3; j++){                        
                        b[i % 3][j % 3] = bigBoard[i][j];                    
                    }
                }
                boards.add(b);
            }
        }
        repaint();
        player = 1;
        
        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    public void instantPlay(){
        int p = (int) (Math.random()*100);
        p = p % 2 + 1;
        player = p;
        if (p == 1){
            while (!p1.hasWonGame()){
                p1.setWin(((int) (Math.random() * 1000)) % 9);
            }
        }
        else{
            while (!p2.hasWonGame()){
                p2.setWin(((int) (Math.random() * 1000)) % 9);
            }
        }
        repaint();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}