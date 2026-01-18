package apps;

import java.util.Random;

public class OtherApp {
	
	public String result(String c) {
		
		String[] idk = new String[3];
		idk[0] = "Sorry, I didn't catch that. Could you say it another way?";
		idk[1] = "Can you clarify what you mean?";
		idk[2] = "I'm not sure I understand—can you say that differently?";
		
		 Random random = new Random();
		 int randomInt = random.nextInt(3);
		
		return idk[randomInt];
	}

	public int check(String c) {
		return 1;
	}

}
