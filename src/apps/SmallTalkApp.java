package apps;

import java.util.Random;

public class SmallTalkApp {
	
	public String result(String c, ChatContextApp context) {
		
		if(c.contains("name") && c.contains("your")) {
			return "I'm Bob, your AI assistant! What should I call you?";
		}else if((c.contains("how") && c.contains("are") && c.contains("you") && !c.contains("old")) || (c.contains("how") && c.contains("you") && c.contains("feel") && !c.contains("old"))) {
			
			String[] feeling = new String[6];
			feeling[0] = "I'm doing great! Ready to help you out!";
			feeling[1] = "Pretty good! How are you doing?";
			feeling[2] = "Feeling fantastic, thanks for asking!";
			feeling[3] = "I'm operating at peak performance! What about you?";
			feeling[4] = "Excellent! Always happy to assist.";
			feeling[5] = "I'm feeling AMAZING!";
			
			Random random = new Random();
			int randomInt = random.nextInt(6);
			return feeling[randomInt];
			
		}else if(c.contains("old") && c.contains("you")) {
			return "I'm as timeless as artificial intelligence itself!";
		}else if(c.contains("thanks") || c.contains("thank you") || c.contains("thx")){
			return "Happy to help! Let me know if you need anything else.";
		}
		else if(c.contains("you") && c.contains("favorite")){
			return "As an AI, I don't have personal preferences, but I'm designed to help YOU find yours!";
		}
		else if(c.equals("ok") || c.contains("okay") || c.equals("ok.") || c.equals("k") || c.equals("no") || c.equals("yes")){
			return "Is there anything else I can help you with?";
		}
		else if(c.contains("my name is")){
			
			context.userName = Character.toUpperCase(c.charAt(c.indexOf("my name is") + 11)) + (c.substring(c.indexOf("my name is") + 12).split(" ")[0]); // finds user's name
			
			return "Nice to meet you, " + context.userName + "! Let's do something great together.";
		}
		else if((c.contains("how") || c.contains("can")) && (c.contains("productive") || c.contains("work") || c.contains("study")) && (c.contains("help") || c.contains("aid"))){ // productivity
			
			return "I can boost your productivity by:\n -Managing your to-do lists and tasks\n -Keeping you on schedule with time reminders\n -Checking the weather for you\n -And more coming soon!";
		}
		else if((c.contains("how") || c.contains("can")) && c.contains("you") && (c.contains("entertain") || c.contains("play") || c.contains("entartain"))){ // entertainment
			
			return "I can keep you entertained with:\n -Fun games and challenges\n -Witty jokes and humor\n -Engaging conversations\n -And more surprises on the way!";
		}
		else if((c.contains("what") || c.contains("how")) && c.contains("you") && (c.contains("help") || c.contains("do"))){ // what can Bob do
			
			return "I'm your all-in-one assistant! I can help you stay organized with productivity tools, or keep you entertained with games and jokes. Try asking me about the weather, your to-do list, the time, or a joke!";
		}
		
		return "Tell me more! What's on your mind?"; // default
	}
	

	public int check(String c) {
		if(c.contains("name") && c.contains("your")) {
			return 10;
		}
		else if((c.contains("how") && c.contains("are") && c.contains("you")) || (c.contains("how") && c.contains("you") && c.contains("feel"))) {
			return 8;
		}
		else if(c.contains("old") && c.contains("you")) {
			return 9;
		}
		else if(c.contains("thanks") || c.contains("thank you") || c.contains("thx")){
			return 7;
		}
		else if(c.contains("you") && c.contains("favorite")){
			return 6;
		}
		else if(c.contains("what") && c.contains("you") && c.contains("favorite")){
			return 8;
		}
		else if(c.equals("ok") || c.contains("okay") || c.equals("ok.") || c.equals("k") || c.equals("no") || c.equals("yes")){ // check for "ok"
			return 7;
		}
		else if(c.contains("my name is")){ // check for user giving name
			return 8;
		}
		else if((c.contains("how") || c.contains("can")) && (c.contains("productive") || c.contains("work") || c.contains("study")) && (c.contains("help") || c.contains("aid"))){ // productivity
			return 6;
		}
		else if((c.contains("how") || c.contains("can")) && c.contains("you") && (c.contains("entertain") || c.contains("play") || c.contains("entartain"))){ // entertainment
			return 6;
		}
		else if((c.contains("what") || c.contains("how")) && c.contains("you") && (c.contains("help") || c.contains("do"))){ // what can Bob do
			return 6;
		}
		
		return 0; // default
	}

}