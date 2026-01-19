package apps;

import seperateApps.*;

public class SystemApp {
	
	/*
	 * Handles Commands (commands are special, strict orders that begin with /)
	 */
	
	public String result(String c, ChatContextApp context) {
		
		if(c.equals("/exit")) { // EXIT
			
			context.running = false; // Exits the whole program
			
			if(!context.userName.equals(""))
			{
				return "Goodbye " + context.userName + "! Hope you can chat again sometime soon!";
			}
			else
			{
				return "Goodbye! Hope you can chat again sometime soon!";
			}
			
		}else if(c.equals("/help")) { // HELP
			
			return "Here is the list of available commands:\n    /calc -> use the built-in calculator\n    /timer -> launch a timer\n    /exit -> exit the program\n    /help -> view available commands\n    /debug -> toggle debugging mode";
			
		}else if(c.equals("/debug")) { // DEBUG
			
			if(context.debugging)
			{
				context.debugging = false;
				return "Debugging mode has been turned off.";
			}
			else
			{
				context.debugging = true;
				return "Debugging mode has been turned on.";
			}
			
		}else if(c.length() >= 5 && c.substring(0, 5).equals("/calc")) { // CALC
			
			final String CALC_FORMAT_ERROR = "The '/calc' function uses the following format, where x = any number and o = {+, -, *, /, or ^}:  /calc x o x";
			
			if(c.equals("/calc")) {
				return CALC_FORMAT_ERROR;
			}
			
			if(c.substring(0, 6).equals("/calc ")) {
				
				String[] equation = c.substring(5).trim().split("\\s+");
				
				if(equation.length == 3) { // correct length
					
					try {
						switch(equation[1]){
							case("+"):
								return String.valueOf(Double.parseDouble(equation[0]) + Double.parseDouble(equation[2]));
							case("-"):
								return String.valueOf(Double.parseDouble(equation[0]) - Double.parseDouble(equation[2]));
							case("*"):
								return String.valueOf(Double.parseDouble(equation[0]) * Double.parseDouble(equation[2]));
							case("/"):
								if(Integer.parseInt(equation[2]) == 0) {return "Error: cannot divide by zero";}
								return String.valueOf(Double.parseDouble(equation[0]) / Double.parseDouble(equation[2]));
							case("^"):
								return String.valueOf(Math.pow(Double.parseDouble(equation[0]), Double.parseDouble(equation[2])));
							default:
								return CALC_FORMAT_ERROR;
						}
					}catch(Exception e) {
						return CALC_FORMAT_ERROR;
					}
					
					//return "Your command: " + Arrays.toString(equation) + "Length: " + equation.length;
				}
				
				return CALC_FORMAT_ERROR; // wrong amount of input parameters
			}
		}else if(c.length() >= 6 && c.substring(0, 6).equals("/timer")) { // CUSTOM TIMER
			
			final String TIMER_FORMAT_ERROR = "The '/timer' function uses the following format, where m = minutes and s = seconds:  /timer m s";
			
			String[] input = c.trim().split("\\s+");
			
			if(input.length != 3) {
				return TIMER_FORMAT_ERROR;
			}
			
			int minutes = 0;
			int seconds = 0;
			
			try {
				
				minutes = Integer.parseInt(input[1]);
				seconds = Integer.parseInt(input[2]);
				
			}catch(Exception e) {
				return TIMER_FORMAT_ERROR;
			}
			
			// check to ensure that minutes and seconds are valid:
			if(((minutes < 0) || (seconds < 0)) || ((minutes == 0) && (seconds == 0)) || ((minutes > 60) || (seconds > 59))) {
				return "Please enter valid values!";
			}
			
			new TimerApp(minutes, seconds);
			return "Your timer has been launched.";
			
		}
			
		return "The '/' operator is reserved for commands, please. Use '/help' to view a list of available commands."; // DEFAULT
	}

	public int check(String c) {
		
		if(c.charAt(0) == '/') {
			return 11;
		} else {
			return 0;
		}
	}

}