package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import apps.*;

/*
	NOTE: This is the main class -> it runs the chatbot in a GUI window.

	*Also, the main class is at the bottom of this file*
*/

@SuppressWarnings("serial")
public class MainWindowLauncher extends JFrame {
	
	private JTextArea conversationArea;
	private JTextField inputField;
	private JButton sendButton;
	private PrintWriter fileWriter;
	private ChatContextApp context;
	private ArrayList<AppWrapper> apps;
	
	public MainWindowLauncher() {
		
		setTitle("Bob - Your AI Assistant");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 700);
		setLocationRelativeTo(null);
		setResizable(true);
		
		// Initialize file writer for conversation history
		try {
			fileWriter = new PrintWriter(new FileWriter("conversationHistory.out", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Initialize context and apps
		context = new ChatContextApp();
		initializeApps();
		
		// Create UI
		setupUI();
		
		// Display greeting
		String greeting = "Hey there! I'm Bob, your AI assistant. I'm here to help you stay productive and keep you entertained!\n\nI can:\n -Manage your to-do lists and reminders\n -Tell you the time and weather\n -Play games with you\n -Tell jokes and chat with you\n -And much more!\n\nWhat can I help you with today?";
		conversationArea.append("Bob: " + greeting + "\n\n");
		fileWriter.println("Bob: " + greeting);
		fileWriter.flush();
		
		setVisible(true);
	}
	
	private void setupUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		// Conversation area
		conversationArea = new JTextArea();
		conversationArea.setEditable(false);
		conversationArea.setLineWrap(true);
		conversationArea.setWrapStyleWord(true);
		conversationArea.setFont(new Font("Arial", Font.PLAIN, 13));
		JScrollPane scrollPane = new JScrollPane(conversationArea);
		
		// Input panel
		JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		inputField = new JTextField();
		inputField.setFont(new Font("Arial", Font.PLAIN, 13));
		inputField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
		});
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(e -> sendMessage());
		
		inputPanel.add(inputField, BorderLayout.CENTER);
		inputPanel.add(sendButton, BorderLayout.EAST);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(inputPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
	}
	
	private void initializeApps() {
		/*
			UPDATE THIS METHOD WHENEVER A NEW APP IS ADDED
		*/
		apps = new ArrayList<>();
		apps.add(new AppWrapper(new TimeApp(), "time"));
		apps.add(new AppWrapper(new OtherApp(), "other"));
		apps.add(new AppWrapper(new GreetApp(), "greet"));
		apps.add(new AppWrapper(new JokeApp(), "joke"));
		apps.add(new AppWrapper(new SmallTalkApp(), "smalltalk"));
		apps.add(new AppWrapper(new ExitApp(), "exit"));
		apps.add(new AppWrapper(new SystemApp(), "system"));
		apps.add(new AppWrapper(new WeatherApp(), "weather"));
		apps.add(new AppWrapper(new ToDoListApp(), "todo"));
		apps.add(new AppWrapper(new GameApp(), "games"));
	}
	
	private void sendMessage() {
		String userInput = inputField.getText().trim();
		
		if (userInput.isEmpty()) {
			return;
		}
		
		// Display user message
		conversationArea.append("You: " + userInput + "\n");
		fileWriter.println("User: " + userInput);
		fileWriter.flush();
		
		// Process input
		String command = userInput.toLowerCase();
		String response = processCommand(command);
		
		// Display Bob's response
		conversationArea.append("Bob: " + response + "\n\n");
		fileWriter.println("Bob: " + response);
		fileWriter.flush();
		
		// Clear input field
		inputField.setText("");
		inputField.requestFocus();
		
		// Auto-scroll to bottom
		conversationArea.setCaretPosition(conversationArea.getDocument().getLength());
		
		// Check if user wants to exit
		if (context.running == false) {
			try {
				fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	private String processCommand(String command) {
		if (command.length() == 0) {
			return "Is there anything else I can help you with?";
		}
		
		// Find app with highest probability
		AppWrapper chosenApp = apps.stream()
			.max((a, b) -> Integer.compare(a.check(command), b.check(command)))
			.orElse(apps.get(0));
		
		// Get response
		return chosenApp.getResult(command, null, context);
	}
	
	// Wrapper class for apps
	private static class AppWrapper {
		private Object app;
		
		public AppWrapper(Object app, String name) {
			this.app = app;
		}
		
		public int check(String command) {
			try {
				return (int) app.getClass().getMethod("check", String.class).invoke(app, command);
			} catch (Exception e) {
				return 0;
			}
		}
		
		public String getResult(String command, BufferedReader br, ChatContextApp context) {
			try {
				// Try different method signatures until one works:
				try {
					return (String) app.getClass()
						.getMethod("result", String.class, BufferedReader.class, ChatContextApp.class)
						.invoke(app, command, br, context);
				} catch (NoSuchMethodException e1) {
					try {
						return (String) app.getClass()
							.getMethod("result", String.class, BufferedReader.class)
							.invoke(app, command, br);
					} catch (NoSuchMethodException e2) {
						try {
							return (String) app.getClass()
								.getMethod("result", String.class, ChatContextApp.class)
								.invoke(app, command, context);
						} catch (NoSuchMethodException e3) {
							return (String) app.getClass()
								.getMethod("result", String.class)
								.invoke(app, command);
						}
					}
				}
			} catch (Exception e) {
				return "Sorry, there was an error: " + e.getMessage();
			}
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainWindowLauncher());
	}
}
