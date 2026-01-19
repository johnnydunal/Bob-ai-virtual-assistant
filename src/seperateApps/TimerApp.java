package seperateApps;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.SoundPlayer;

public class TimerApp {
	
	private int maxMinutes = 0;
	private int maxSeconds = 0;
	
	private int minutes = maxMinutes;
	private int seconds = maxSeconds;
	private boolean timerRunning = false;
	private boolean stopTimer = false;

	public TimerApp(int minutesNeeded, int secondsNeeded) {
		
		maxMinutes = minutesNeeded;
		maxSeconds = secondsNeeded;
		
		// Window Setup:
		
		JFrame window = new JFrame("Timer");
		
		Color c = new Color(23, 232, 23); // greenish color
		
		// Stops the timer if the window is closed:
		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				stopTimer = true;
			}
		});
		
		window.setLayout(new GridLayout(3, 1));
		
		window.getContentPane().setBackground(c);
		
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(360, 240);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		
		// Text that shows time left on timer
		JLabel timerLabel = new JLabel("", SwingConstants.CENTER);
		timerLabel.setFont(new Font("Arial", Font.BOLD, 32));
		displayTime(timerLabel, false);
		
		// Pause Button
		JButton pauseButton = new JButton("Start / Pause");
		pauseButton.setFont(new Font("Arial", Font.BOLD, 24));
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timerRunning = !timerRunning;
				SoundPlayer.playSound("pause_sound.wav");
			}
		});
		pauseButton.setBackground(c);
		
		// Reset Button
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Arial", Font.BOLD, 24));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				stopTimer = true; // stops all other timers
				timerLabel.setText("Resetting...");
				
				Timer oneSecondTimer = new Timer(1100, event -> {
					// After 1 second has passed:
					stopTimer = false;
					displayTime(timerLabel, false);
					SoundPlayer.playSound("ding.wav");
					makeTimer(maxMinutes, maxSeconds, timerLabel, "Time's Up!");
				});
				oneSecondTimer.setRepeats(false); // only runs once
				oneSecondTimer.start();
			}
		});
		resetButton.setBackground(c);
		
		// Timer
		makeTimer(maxMinutes, maxSeconds, timerLabel, "Time's Up!");
		
		window.add(timerLabel);
		window.add(pauseButton);
		window.add(resetButton);
		window.setVisible(true);

	}
	
	private void makeTimer(int m, int s, JLabel l, String timeUpMessage) {
		
		// Update the length of the timer
		minutes = m;
		seconds = s;
		
		timerRunning = false; // Starts false so that user can start the timer
		
		int delay = 1000; // milliseconds (1000 ms = 1 second)
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				// Update Timer:
				
				if(stopTimer) {
					timerRunning = false;
					((Timer)evt.getSource()).stop();
				}
				
				if(timerRunning) { // Only update timer if it's supposed to be updated
					
					seconds--;
					if(seconds < 0) {
						seconds = 59;
						minutes--;
					}
					
					displayTime(l, true); // Update Timer Text
					
					if(minutes <= 0 && seconds <= 0) { // timer runs out
						l.setText(timeUpMessage);
						timerRunning = false;
						SoundPlayer.playSound("timer_finished.wav");
						((Timer)evt.getSource()).stop();
					}
					
				}
			}
		};
		new Timer(delay, taskPerformer).start();
		
	}
	
	private void displayTime(JLabel l, boolean currentTime) {
		
		// Properly formats and displays the timer's time in the provided JLabel
		
		// currentTime boolean specifies whether the current timer time should be diplayed
		//  -if false, displays the max timer time
		
		String formattedMinutes;
		String formattedSeconds;
		
		if(currentTime) {
			formattedMinutes = minutes + "";
			formattedSeconds = seconds + "";
		}
		else {
			formattedMinutes = maxMinutes + "";
			formattedSeconds = maxSeconds + "";
		}
		
		if(Integer.parseInt(formattedSeconds) < 10) {
			formattedSeconds = "0" + formattedSeconds;
		}
		
		l.setText(formattedMinutes + ":" + formattedSeconds);
		
	}

}