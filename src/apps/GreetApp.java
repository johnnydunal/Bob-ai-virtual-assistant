package apps;

import java.util.Random;

public class GreetApp {
	
	public String result(String c) {
		
		String[] greetings = new String[8];
		greetings[0] = "Hey there! Great to see you!";
		greetings[1] = "Hello! What can I help you with today?";
		greetings[2] = "Hi! How's it going?";
		greetings[3] = "Howdy! Ready to get things done?";
		greetings[4] = "Hello! Happy to assist you!";
		greetings[5] = "Hey! What's up?";
		greetings[6] = "Hi! Glad you stopped by!";
		greetings[7] = "Greetings! Let's make today awesome!";
		
		 Random random = new Random();
		 int randomInt = random.nextInt(8);
		
		return greetings[randomInt];
	}

	public int check(String c) {
		if(c.equals("hi ") || c.equals("hi!") || c.contains("hello") || c.contains("howdy") || c.contains("good morning") || c.contains("good afternoon") || c.contains("good evening") || c.contains("hey")) {
			return 10;
		}else if(c.contains("greetings") || c.contains("bonjour") || c.contains("ola") || c.contains("hi")) {
			return 5;
		}else {
			return 0;
		}
	}

}
