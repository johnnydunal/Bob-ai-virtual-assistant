package apps;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import seperateApps.*;

public class TimeApp {
	
	public String result(String c) {
		
		if(c.contains("timer")) {
			
			//TODO: Allow user to select timer length
			
			new TimerApp(0, 10);
			
			return "Your 10-second timer has been launched.";
			
		}else {
			
			// Display Time
			
			LocalTime t = LocalTime.now();
			
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
	        String formattedTime = t.format(formatter);
			
			return "The time is " + formattedTime;
			
		}
	}

	public int check(String c) {
		if(c.contains("timer")) {
			return 10;
		}else if(c.contains("time") || c.contains("tmie")) {
			return 9;
		}else if(c.contains("watch") || c.contains("clock") || c.contains("tome")){
			return 3;
		}else {
			return 0;
		}
	}

}
