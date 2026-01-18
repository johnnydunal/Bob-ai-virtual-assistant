package apps;

import java.io.*;
import java.util.*;

public class ToDoListApp {
	
	/*
	 * 
	 * To-Do List App: Uses the "toDoList.txt" file to store the user's to-do list
	 * 
	 * In the "toDoList.txt" file, THE FIRST LINE CONTAINS THE LIST'S LENGTH.
	 * 
	 * For example:
	 * 
	 *  2
	 *  a
	 *  b
	 * 
	 */
	
	public String result(String c, BufferedReader br) throws IOException {
		
		if(c.contains("view") || c.contains("show") || c.contains("see")) {
			
			return viewList();
			
		}else if(c.contains("clear") || c.contains("remove all")) {
			
			return clearList();
			
		}else if(c.contains("add") || c.contains("include")) {
			
			return addToList(br);
			
		}else if(c.contains("remove") || c.contains("take out") || c.contains("finished")) {
			
			return removeFromList(br);
			
		}else if(c.contains("sort") || c.contains("arrange") || c.contains("order")){
			
			return sortList();
		}
		
		return "Sorry, I don't quite understand.\nIf you're trying to navigate your to-do list, try one of the following:\n -Viewing your list\n -Adding items to your list\n -Removing items from your list\n -Clearing your list\n -Sorting your list"; // Default/Backup Case
	}
	

	public int check(String c) {
		
		if(c.contains("to do") || c.contains("to-do") || c.contains("todo")) {
			return 9;
		}else if(c.contains("checklist") || c.contains("list")) {
			return 7;
		}else if(c.contains("lit")) {
			return 3;
		}else {
			return 0;
		}
	}
	
	
	public String addToList(BufferedReader br) throws IOException{ // lets user add items to list
		
		String[] list = getList();
		
		try
		{
			
			System.out.println("How many items would you like to add to your to-do list? (Please respond with a number. Maximum is 20)");
			int amountToAdd = Integer.parseInt(br.readLine());
			
			if(amountToAdd > 20)
			{
				amountToAdd = 20;
			}
			else if(amountToAdd == 0)
			{
				return "Ok.";
			}
			String[] items = new String[amountToAdd];
						
			System.out.println("Please enter your " + amountToAdd + " item(s), each on seperate lines:");
			for(int i = 0;i < amountToAdd;i++)
			{
				items[i] = br.readLine();
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter("toDoList.txt", false));
			
			list[0] = String.valueOf(Integer.parseInt(list[0]) + amountToAdd);
			
			for(int i = 0;i < list.length;i++) // rewrite old list content
			{
				pw.println(list[i]);
			}
			for(int i = 0;i < amountToAdd;i++) // add the new items
			{
				pw.println(items[i]);
			}
			
			pw.close();
			pw.flush();
			
			return "Those items have been added to your to-do list.";
		}
		catch(Exception e)
		{
			return "There was an error accessing/modifying your to-do list: " + e;
		}
		
	}


	public String removeFromList(BufferedReader br) throws IOException{ // lets user remove items from list
	
		String[] list = getList();
	
		try
		{
			
			System.out.println("What would you like to remove from your to-do list?");
			String itemToRemove = br.readLine();
			
			ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(list));
			list2.remove(0); // removing the list length
			
			boolean removed = list2.remove(itemToRemove);
			
			if(!removed)
			{
				return "This item was not found in your list. Check for typos!";
			}
			
			PrintWriter pw = new PrintWriter(new FileWriter("toDoList.txt", false));
			
			pw.println(list2.size()); // print the list's length
			
			for(int i = 0;i < list2.size();i++) // rewrite list content
			{
				pw.println(list2.get(i));
			}
			
			pw.close();
			pw.flush();
			
			return "'" + itemToRemove + "' has been removed from your to-do list.";
		}
		catch(Exception e)
		{
			return "There was an error accessing your to-do list: " + e;
		}
	
	}
	
	
	public String sortList() throws IOException {
		
		String[] list = getListWithoutListLength();
		
		Arrays.sort(list);
		
		PrintWriter pw = new PrintWriter(new FileWriter("toDoList.txt", false));
		
		pw.println(list.length);
		
		for(int i = 0;i < list.length;i++) // print the (sorted) list content
		{
			pw.println(list[i]);
		}
		
		pw.flush();
		pw.close();
		
		return "Your list was sorted successfully.";
	}
	
	
	public String viewList() { // lets user view list
		
		String[] list = getList();
		
		if(list.length == 0 || list[0].equals("0")) // in case list is empty
		{
			return "Your to-do list is empty.";
		}
		
		String formattedList = "Your to-do list contains:";
		
		for(int i = 1;i < list.length;i++) // starts at 1 in order to not return the list's length
		{
			formattedList += "\n  " + list[i];
		}
		
		return formattedList;
		
	}
	
	
	public String clearList() { // lets user clear list
		
		try
		{
			PrintWriter pw = new PrintWriter(new FileWriter("toDoList.txt", false));
			
			pw.print(0);
			pw.close();
			pw.flush();
			
			return "Your to-do list has been cleared successfully.";
		}
		catch(Exception e)
		{
			return "There was an error while clearing your to-do list: " + e;
		}
	}
	
	
	public String[] getList() { // retrieves list from file -> including list_length number
		
		try (BufferedReader br = new BufferedReader(new FileReader("toDoList.txt"))) {
			
			int listLength = Integer.parseInt(br.readLine());
			
			if(listLength == 0)
			{
				return new String[]{"0"};
			}
			
			String[] list = new String[listLength + 1];
			list[0] = String.valueOf(listLength);
			
			for(int i = 1;i < listLength + 1;i++)
			{
				list[i] = br.readLine();
			}
			
			return list;
			
		}
		catch (Exception e) {
			return new String[]{"0"};
		}
	}
	
	
	public String[] getListWithoutListLength() { // retrieves list from file -> NOT including list_length number
		
		try (BufferedReader br = new BufferedReader(new FileReader("toDoList.txt"))) {
			
			int listLength = Integer.parseInt(br.readLine());
			
			if(listLength == 0)
			{
				return new String[0];
			}
			
			String[] list = new String[listLength];
			
			for(int i = 0;i < listLength;i++)
			{
				list[i] = br.readLine();
			}
			
			return list;
			
		} catch (Exception e) {
			return new String[0];
		}
	}

}