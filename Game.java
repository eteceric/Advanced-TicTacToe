

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("ADVANCED TIC TAC TOE");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        try{
            
        
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        final JButton startGame = new JButton("Start New Game");
        startGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                court.startNewGame();
            }
        });
        control_panel.add(startGame);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        
        final JButton rematch = new JButton("Rematch");
        rematch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                court.rematch();
            }
        });
        control_panel.add(rematch);
        
        final JButton instantPlay = new JButton("Instant Play");
        instantPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.instantPlay();
            }
        });
        control_panel.add(instantPlay);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.rematch();
        }
        catch(IOException e){}
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}