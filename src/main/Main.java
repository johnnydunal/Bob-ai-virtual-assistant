package main;

import java.io.*;
import java.util.*;

import apps.*;

/*
	NOTE: This is the old main class -> it runs the chatbot in console mode.
		The new main class is MainWindowLauncher.java -> it runs the chatbot in a GUI window.
		The old main class is kept here for reference and backup purposes.
*/

public class Main {

	public static void main(String[] args) throws IOException{
		
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		
		PrintWriter pw = new PrintWriter("conversationHistory.out"); // For saving conversation history
		
		// Original Greeting
		System.out.println("Hey there! I'm Bob, your AI assistant. I'm here to help you stay productive and keep you entertained!\n\nI can:\n -Manage your to-do lists and reminders\n -Tell you the time and weather\n -Play games with you\n -Tell jokes and chat with you\n -And much more!\n\nWhat can I help you with today?");
		pw.println("Hey there! I'm Bob, your AI assistant. I'm here to help you stay productive and keep you entertained!\\n\\nI can:\\n -Manage your to-do lists and reminders\\n -Tell you the time and weather\\n -Play games with you\\n -Tell jokes and chat with you\\n -And much more!\\n\\nWhat can I help you with today?"); // Saving conversation history
		
		// SETUP - STEP 1
		TimeApp time = new TimeApp();
		OtherApp other = new OtherApp();
		GreetApp greet = new GreetApp();
		JokeApp joke = new JokeApp();
		SmallTalkApp smalltalk = new SmallTalkApp();
		ExitApp exit = new ExitApp();
		SystemApp system = new SystemApp();
		WeatherApp weather = new WeatherApp();
		ToDoListApp todo = new ToDoListApp();
		GameApp games = new GameApp();
		
		ChatContextApp context = new ChatContextApp(); // For saving chat context (for example, userName)
		
		context.running = true;
		while(context.running) {
			
			String originalCommand = r.readLine();
			String command = originalCommand.toLowerCase().trim();
			String answer = "Good Job! You broke the system! :)"; // Never actually happens :)
			
			if(command.length() == 0) // in case user typed nothing in
			{
				System.out.println("Is there anything else I can help you with?");
				continue;
			}
			
			pw.println("User: " + command); // Saving conversation history
			
			// PROBABILITY - STEP 2
			int timeProb = time.check(command);
			int otherProb = other.check(command);
			int greetProb = greet.check(command);
			int jokeProb = joke.check(command);
			int smalltalkProb = smalltalk.check(command);
			int exitProb = exit.check(command);
			int systemProb = system.check(command);
			int weatherProb = weather.check(command);
			int todoProb = todo.check(command);
			int gameProb = games.check(command);
			
			// FINDING CHOSEN APP - STEP 3
			int chosenApp = Collections.max(Arrays.asList(timeProb, otherProb, greetProb, jokeProb, smalltalkProb, exitProb, systemProb, weatherProb, todoProb, gameProb));
			
			// GETTING RESULT OF CHOSEN APP - STEP 4
			if(timeProb == chosenApp) {
				answer = time.result(command);
			}else if(otherProb == chosenApp){
				answer = other.result(command);
			}else if(greetProb == chosenApp) {
				answer = greet.result(command);
			}else if(jokeProb == chosenApp) {
				answer = joke.result(command);
			}else if(smalltalkProb == chosenApp) {
				answer = smalltalk.result(command, context);
			}else if(exitProb == chosenApp) {
				answer = exit.result(command, context);
			}else if(systemProb == chosenApp) {
				answer = system.result(command, context);
			}else if(weatherProb == chosenApp) {
				answer = weather.result(command, context);
			}else if(todoProb == chosenApp) {
				answer = todo.result(command, r);
			}else if(gameProb == chosenApp) {
				answer = games.result(command, r);
			}
			
			System.out.println(answer);
			pw.println("Bob: " + answer); // Saving conversation history
			pw.flush();
			
			// UPDATE DEBUGGING OUTPUT - STEP 5
			if(context.debugging) { System.out.println(timeProb + ", " + otherProb + ", " + greetProb + ", " + jokeProb + ", " + smalltalkProb + ", " + exitProb + ", " + systemProb + ", " + weatherProb + ", " + todoProb + ", " + gameProb);} // Debugging Stuff
			
		}
		
		pw.close(); // Closes PrintWriter
		
	}
}
