package apps;

import java.io.*;
import java.util.*;

public class GameApp {
	
	/*
	 * Game App: Can play simple games with the user
	 * 
	 * Games Currently Available:
	 *  1) Tic Tac Toe
	 *  2) Guess the number
	 *  3) Wordle (Guess the Word)
	 * 
	 */
	
	public String result(String c, BufferedReader br) throws IOException {
		
		if(c.contains("gg") || c.contains("good game")) // check is the user was saying 'gg'
		{
			return "Yes, Good Game!";
		}
		
		// Game Selection:
		switch(verifyAndGetGame(br))
		{
			case -2: // Error
				return "Sorry, there was an error while preparing your game.";
			case -1: // Wrong user input
				return "Please enter a valid game.";
			case 0: // User says no
				return "Ok, is there anything else I can help you with?";
			case 1: // Tic-Tac-Toe game
				return playTicTacToe(br);
			case 2:
				return playGuessTheNumber(br);
			case 3:
				return playWordle(br);
			case 4:
				return playRockPaperScissors(br);
			default:
				return "Please try again.";
		}
	}
	

	public int check(String c) {
		
		if((c.contains("play") && c.contains("game")) || ((c.contains("lets") || c.contains("let's")) && c.contains("play"))) {
			return 9;
		}else if(c.contains("start") && c.contains("game")) {
			return 7;
		}else if(c.contains("play") || c.contains("game")) {
			return 4;
		}else if(c.contains("gg") || c.contains("good game")){
			return 2;
		}
		
		return 0;
	}
	
	
	public int verifyAndGetGame(BufferedReader br) { // lets user verify that they want to play, and then choose their game
		
		try {
			
			System.out.println("Would you like to play a game? (please answer either 'yes' or 'no')");
			String answer1 = br.readLine();
			
			if(!(answer1.contains("yes") || answer1.equals("yse") || answer1.equals("sure"))) // user says no
			{
				return 0;
			}
			
			//TODO: Implement multi-message communications!
			
			// If user wants to play game:
			System.out.println("Which of the following games would you like to play?\n 1) Tic Tac Toe\n 2) Guess the Number\n 3) Wordle / Guess the Word\n 4) Rock Paper Scissors");
			String answer2 = br.readLine();
			
			// Check which game user wants to play:
			if(answer2.contains("ttt") || (answer2.contains("tic") && answer2.contains("tac") && answer2.contains("toe")) || answer2.contains("1"))
			{
				return 1;
			}
			else if(answer2.contains("gtn") || (answer2.contains("guess") && answer2.contains("the") && answer2.contains("number")) || answer2.contains("2"))
			{
				return 2;
			}
			else if(answer2.contains("hangman") || (answer2.contains("guess") && answer2.contains("the") && answer2.contains("word")) || answer2.contains("3"))
			{
				return 3;
			}
			else if(answer2.contains("rock") || (answer2.contains("rps")) || answer2.contains("4"))
			{
				return 4;
			}
		
		return -1; // in case user's prompt doesn't match any games
		
		} catch(Exception e) {
			return -2; // in case an error occured
		}
	}
	
	
	public String playTicTacToe(BufferedReader br) // plays tic tac toe with the user
	{
		
		try {
			
			// X = User, O = Bob
		
			String[] grid = new String[] {" ", " ", " ", " ", " ", " ", " ", " ", " "};
			int emptySpots = 9;
		
			System.out.println("Are you ready...to play...TIC TAC TOE!");
			System.out.println("You, as the user, will go first. To enter the place you want to choose, use the corresponding number:\n" + " 1 | 2 | 3 " + "\n---|---|---" + "\n 4 | 5 | 6 " + "\n---|---|---" + "\n 7 | 8 | 9 ");
			
			Random r = new Random(); //  for generating Bob's response
		
			// Code that displays the current grid layout:
			// System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2] + " " + "\n---|---|---" + "\n " + grid[3] + " | " + grid[4] + " | " + grid[5] + " " + "\n---|---|---" + "\n " + grid[5] + " | " + grid[7] + " | " + grid[8] + " ");
		
			while(true) { // loop that plays tic-tac-toe	
				
				int userSpot = Integer.parseInt(br.readLine()); // read in user's input
				if(!grid[userSpot - 1].equals(" ")) {
					return "Woah! that spot is already taken. Are you trying to cheat?";
				}
				grid[userSpot - 1] = "X";
				emptySpots--;
				
				if(ticTacToeWinnerChecker(grid, "X")) // check if user has won yet
				{
					System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2] + " " + "\n---|---|---" + "\n " + grid[3] + " | " + grid[4] + " | " + grid[5] + " " + "\n---|---|---" + "\n " + grid[6] + " | " + grid[7] + " | " + grid[8] + " ");
					System.out.println("YOU WON!\nIf you ever want a challenge, try playing for a tie. It's surprisingly difficult :)");
					break;
				}
				
				// check if there are any spots remaining
				if(emptySpots == 0)
				{
					System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2] + " " + "\n---|---|---" + "\n " + grid[3] + " | " + grid[4] + " | " + grid[5] + " " + "\n---|---|---" + "\n " + grid[6] + " | " + grid[7] + " | " + grid[8] + " ");
					System.out.println("It's a tie!");
					break;
				}
				
				// calculate Bob's response
				System.out.println("Bob's response:");
				int bobAnswer = r.nextInt(emptySpots);
				
				int spotsSeen = 0;
				for(int i = 0;i < grid.length;i++) { // loop through grid to see which spot Bob chose
					if(grid[i].equals(" ")) // check if grid spot is empty
					{
						if(bobAnswer == spotsSeen)
						{
							grid[i] = "O";
							break;
						}
						spotsSeen++;
					}
				}
				emptySpots--;
				
				System.out.println(" " + grid[0] + " | " + grid[1] + " | " + grid[2] + " " + "\n---|---|---" + "\n " + grid[3] + " | " + grid[4] + " | " + grid[5] + " " + "\n---|---|---" + "\n " + grid[6] + " | " + grid[7] + " | " + grid[8] + " ");
				
				// check if Bob has won
				if(ticTacToeWinnerChecker(grid, "O"))
				{
					System.out.println("You lost. Bob Wins!");
					break;
				}
				
				// prompt user to go again
				System.out.println("It's your turn again. Enter a number showing which spot you would like to choose.");
				
			}
		
			return "I hope you had fun playing tic-tac-toe!";
		
		}
		catch(Exception e)
		{
			return "Sorry, there was an error: " + e;
		}
		
	}
	
	
	public boolean ticTacToeWinnerChecker(String[] grid, String letterToCheck)
	{
		
		if(grid[0].equals(letterToCheck) && grid[1].equals(letterToCheck) && grid[2].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[3].equals(letterToCheck) && grid[4].equals(letterToCheck) && grid[5].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[6].equals(letterToCheck) && grid[7].equals(letterToCheck) && grid[8].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[0].equals(letterToCheck) && grid[3].equals(letterToCheck) && grid[6].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[1].equals(letterToCheck) && grid[4].equals(letterToCheck) && grid[7].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[2].equals(letterToCheck) && grid[5].equals(letterToCheck) && grid[8].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[0].equals(letterToCheck) && grid[4].equals(letterToCheck) && grid[8].equals(letterToCheck))
		{
			return true;
		}
		else if(grid[2].equals(letterToCheck) && grid[4].equals(letterToCheck) && grid[6].equals(letterToCheck))
		{
			return true;
		}
		
		return false;
	}
	
	
	public String playGuessTheNumber(BufferedReader br)
	{
		
		try
		{
			
			System.out.println("Let's play 'Guess The Number!'");
			System.out.println("In this game, Bob chooses a number between 0 and 100 and you try to guess this number.");
			System.out.println("Bob will give you small hints like 'Higher' or 'Lower'.");
			System.out.println("Enter your first guess:");
			
			Random r = new Random();
			int bobNumber = r.nextInt(100);
			
			int guesses = 0;
			
			while(true)
			{
				int userGuess = Integer.parseInt(br.readLine());
				guesses++;
				
				if(userGuess == bobNumber)
				{
					System.out.println("Yes! You guessed it in " + guesses + " tries!");
					break;
				}
				
				if(userGuess < bobNumber)
				{
					System.out.println("Higher");
				}
				else {
					System.out.println("Lower");
				}
				
				if((guesses > 15) && (guesses % 2 == 0)) // check if user gives up
				{
					System.out.println("Do you give up? (respond with yes/no)");
					String userAnswer = br.readLine();
					
					if(userAnswer.contains("yes") || userAnswer.contains("yse"))
					{
						System.out.println("Ok, you'll get it next time!");
						break;
					}
					
					System.out.println("Ok, then enter your next guess:");
				}
			}
			
			return "I hope you had fun playing Guess the Number!";
		}
		catch(Exception e)
		{
			return "Sorry, there was an error: " + e;
		} 
	}
	
	
	public String playWordle(BufferedReader br)
	{
		try
		{
			
			int totalAttemptsAllowed;
			
			System.out.println("Let's play Wordle (Guess the Word)!");
			System.out.println("What difficulty level would you like? (enter 0 for easy, 1 for normal, 2 for hard, 3 for extremely hard, 4 for impossible)");
			
			switch(Integer.parseInt(br.readLine()))
			{
				case 0:
					totalAttemptsAllowed = 15;
					break;
				case 1:
					totalAttemptsAllowed = 9;
					break;
				case 2:
					totalAttemptsAllowed = 6;
					break;
				case 3:
					totalAttemptsAllowed = 3;
					break;
				case 4:
					totalAttemptsAllowed = 1;
					break;
				default:
					System.out.println("Since that number is out of range, I'll set your difficulty to normal.");
					totalAttemptsAllowed = 9;
			}
			
			System.out.println("In this game, Bob chooses a random word and you try to guess this word, letter by letter.");
			System.out.println("You will have a set amount of tries to guess letters (or the word if you think you know it), then you will attempt to guess the actual word.");
			System.out.println("Enter your first guess (enter a single letter please):");
			
			String word = getRandomWordForHangman("200_words", 660);
			
			StringBuilder blankWord = new StringBuilder("_");
			for(int i = 1;i < word.length();i++) // fill in blank word with the correct amount of blanks
			{
				blankWord.append(" _"); // Note: when accesing a certain character/blank, use (i*2) to make sore not to accidentally access a space
			}
			
			int guesses = 0;
			ArrayList<String> correctLettersGuessed = new ArrayList<String>();
			ArrayList<String> wrongLettersGuessed = new ArrayList<String>();
			
			while(true)
			{
				
				String letter = br.readLine().toLowerCase().trim();
				
				if(letter.length() == 0) {
					System.out.println("Please enter a valid letter or word:");
					continue;
				}
				else if(letter.length() == 1) { // user guesses a letter
					
					guesses++;
					
					if(correctLettersGuessed.contains(letter) || wrongLettersGuessed.contains(letter))
					{
						System.out.println("You've already guessed this letter. Guess again:");
						guesses--; // doesn't count as a guess if the letter's been guessed already
						continue;
					}
					else if(word.contains(letter))
					{
						System.out.println("Yes, this letter is present in the word.");
						correctLettersGuessed.add(letter);
						
						// update word with blanks:
						blankWord.setCharAt(word.indexOf(letter)*2, letter.charAt(0));
						
						guesses--; // doesn't count as a guess since user guessed correctly
					}
					else {
						System.out.println("No, this letter is not present in the word.");
						wrongLettersGuessed.add(letter);
					}
				}
				else { // user guesses a word
					
					guesses++;
					
					if(word.equals(letter))
					{
						guesses--;
						System.out.println("Congrats! The word was '" + word + "'. You guessed it with only " + guesses + " mistakes!");
						break;
					}
					
					System.out.println("Not quite!");
				}
				
				if(guesses >= totalAttemptsAllowed)
				{
					System.out.println("You've exhausted all your guesses! The word was '" + word + "'. Better luck next time!");
					break;
				}
				
				// prompt user again and show user the current letters guessed
				System.out.println("Current Word: " + blankWord); // show word with correctly guesses letters
				System.out.println("Letters not present: " + wrongLettersGuessed); // show wrongly guessed letters
				System.out.println("Mistakes Left: " + (totalAttemptsAllowed - guesses)); // show attempts left
				System.out.println("Enter your next guess (either a letter or a word if you think you know the answer):");
				
			}
			
			return "I hope you had fun playing Wordle!";
		}
		catch(Exception e)
		{
			return "Sorry, there was an error: " + e;
		}
	}
	
	
	public String getRandomWordForHangman(String filePath, int fileLength)
	{
		/*
		 * Make sure to only return words with no duplicate characters!
		 */
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			Random r = new Random();
			int chosenWord = r.nextInt(fileLength);
			
			for(int i = 0;i < chosenWord-1;i++)
			{
				@SuppressWarnings("unused")
				String wasted = br.readLine();
			}
			
			String word = br.readLine();
			
			br.close();
			
			return word.toLowerCase();
			
		}
		catch(IOException e)
		{
				
			String[] words = new String[] {"flower", "blue", "fun", "write", "crazy", "crowned"};
			
			Random r = new Random();
			
			return words[r.nextInt(words.length)].toLowerCase();
				
		}
	}
	
	
	public String playRockPaperScissors(BufferedReader br)
	{
		try
		{
			System.out.println("Let's play Rock Paper Scissors!");
			System.out.println("Best of 3 rounds. Enter 'rock', 'paper', or 'scissors' for each round.");
			
			Random r = new Random();
			int bobWins = 0;
			int userWins = 0;
			
			String[] choices = {"rock", "paper", "scissors"};
			
			for(int round = 1; round <= 3; round++)
			{
				System.out.println("\nRound " + round + ": Enter your choice (rock, paper, or scissors):");
				String userChoice = br.readLine().toLowerCase().trim();
				
				if(!userChoice.equals("rock") && !userChoice.equals("paper") && !userChoice.equals("scissors"))
				{
					System.out.println("That's not a valid choice. Please enter rock, paper, or scissors.");
					round--; // replay this round
					continue;
				}
				
				String bobChoice = choices[r.nextInt(3)];
				System.out.println("Bob chose: " + bobChoice);
				
				if(userChoice.equals(bobChoice))
				{
					System.out.println("It's a tie!");
				}
				else if((userChoice.equals("rock") && bobChoice.equals("scissors")) ||
				        (userChoice.equals("paper") && bobChoice.equals("rock")) ||
				        (userChoice.equals("scissors") && bobChoice.equals("paper")))
				{
					System.out.println("You win this round!");
					userWins++;
				}
				else
				{
					System.out.println("Bob wins this round!");
					bobWins++;
				}
				
				System.out.println("Score - You: " + userWins + ", Bob: " + bobWins);
			}
			
			System.out.println("\n=== FINAL RESULT ===");
			if(userWins > bobWins)
			{
				System.out.println("You win! Final score: " + userWins + "-" + bobWins);
			}
			else if(bobWins > userWins)
			{
				System.out.println("Bob wins! Final score: " + bobWins + "-" + userWins);
			}
			else
			{
				System.out.println("It's a tie! Final score: " + userWins + "-" + bobWins);
			}
			
			return "I hope you had fun playing Rock Paper Scissors!";
		}
		catch(Exception e)
		{
			return "Sorry, there was an error: " + e;
		}
	}
	
}