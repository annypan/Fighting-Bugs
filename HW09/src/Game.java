import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game implements Runnable {

	public void run() {
		final JFrame frame = new JFrame("FIGHTING BUGS");
		frame.setLocation(600, 600);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);
        
        final JPanel button_panel = new JPanel();
        final JPanel label_panel = new JPanel();

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        button_panel.add(reset);
        
        // add a button that, when clicked on,
        // produces a pop-up window that displays instructions
        final JButton instruction = new JButton("Instructions");
        String words = "Welcome to Fighting Bugs!"
        		+ "\nOne day you entered your dorm, you found your snacks"
        		+ " all over the floor and a lot of bugs eating them. "
        		+ "Luckily, you have a gun that can shoot pesticides to kill the bugs."
        		+ "\nThe light green bugs are dumb bugs that moves randomly, "
        		+ "while the dark green bugs are clever bugs that knows how to take the shortest path "
        		+ "to your snacks."
        		+ "\nYou can move up, down, left or right by pressing W, S, A and D. "
        		+ "You can shoot your pesticide up, down, left or right by pressing I, K, J and L."
        		+ "\nJust run towards your snacks to save them, and shoot bugs on your way!"
        		+ "\nBe careful: since you have bug phobia, if you touch one, you'll be freaked out"
        		+ ", MERTed, and the game is over."
        		+ "\nTo resume the game, close this window and click on 'Resume'."
        		+ "\nTo freeze the game at any point, click on 'Freeze'."
        		+ "\nTo restart the game, click on 'Reset'.";
        instruction.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent t) {
        		court.playing = false;
        		JOptionPane.showMessageDialog(null, words);
        	}
        });
        button_panel.add(instruction);
        
        // add a button that can freeze the game
        final JButton freeze = new JButton("Freeze");
        freeze.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent t) {
        			court.playing = false;       		
        	}
        });
        button_panel.add(freeze);
        
        // add a button that can resume playing the game
        final JButton resume = new JButton("Resume");
        resume.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent t) {
        		if (court.snackSet.size() != 0) {
        			court.playing = true;
        			court.requestFocusInWindow();
        		}
        	}
        });
        button_panel.add(resume);
        
        // put the two labels that indicate game state onto the screen
        label_panel.add(court.scoreDisplayer);
        label_panel.add(court.timeDisplayer);
        
        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.Y_AXIS));
        control_panel.add(label_panel);
        control_panel.add(button_panel);
        control_panel.add(court.highScoreDisplayer);
        frame.add(control_panel, BorderLayout.NORTH);
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

}
