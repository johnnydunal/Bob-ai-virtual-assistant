package apps;

public class ExitApp {
	
	public String result(String c, ChatContextApp context) {
		
		if(!context.userName.equals(""))
		{
			return "Goodbye " + context.userName + "! (Type '/exit' to exit)";
		}
		else
		{
			return "Goodbye! (Type '/exit' to exit)";
		}
		
	}

	public int check(String c) {
		if(c.contains("bye") || c.contains("farewell") || c.contains("goodbye") || c.contains("so long") || c.contains("see you later")) {
			return 10;
		}else if(c.contains("ciao") || c.contains("au revoir") || c.contains("adios")) {
			return 5;
		}else {
			return 0;
		}
	}

}
