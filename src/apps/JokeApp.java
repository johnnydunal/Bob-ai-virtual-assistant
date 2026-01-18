package apps;

import java.util.Random;

public class JokeApp {
	
	public String result(String c) {
		
		String[] jokes = new String[10];
		jokes[0] = "Parallel lines have so much in common but it’s a shame they’ll never meet.";
		jokes[1] = "Alpacas and llamas are, like, spitting images of each other!";
		jokes[2] = "I discovered a substance that had no mass, and I was like '0mg!'";
		jokes[3] = "I haven’t slept for ten days, because that would be too long.";
		jokes[4] = "There are 3 kinds of people in this world, those who can count and those who can't";
		jokes[5] = "What's up? The Sky";
		jokes[6] = "If at first you don’t succeed, then skydiving isn’t for you.";
		jokes[7] = "If you saw a heat wave, would you wave back?";
		jokes[8] = "My theory of evolution is that Darwin was adopted.";
		jokes[9] = "Some people pick their nose, but I was born with mine.";
		
		 Random random = new Random();
		 int randomInt = random.nextInt(10);
		
		return "Here's one of my favorite jokes: " + jokes[randomInt];
	}

	public int check(String c) {
		if(c.contains("joke") || c.contains("laugh")) {
			return 10;
		}else if(c.contains("prank") || c.contains("funny")) {
			return 5;
		}else if(c.contains("cheer") || c.contains("fun")) {
			return 2;
		}else {
			return 0;
		}
	}

}
