package apps;

public class SystemApp {
	
	/*
	 * Handles Commands (commands are special, strict orders that begin with /)
	 */
	
	public String result(String c, ChatContextApp context) {
		
		if(c.equals("/exit")) {
			
			context.running = false; // Exits the whole program
			
			if(!context.userName.equals(""))
			{
				return "Goodbye " + context.userName + "! Hope you can chat again sometime soon!";
			}
			else
			{
				return "Goodbye! Hope you can chat again sometime soon!";
			}
			
		}else if(c.equals("/help")) {
			
			return "This command is not functional at the moment.";
			
		}else if(c.equals("/debug")) {
			
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
			
		}else {
			
			return "The '/' operator is reserved for commands, please.";
			
		}
	}

	public int check(String c) {
		
		if(c.charAt(0) == '/') {
			return 11;
		} else {
			return 0;
		}
	}

}