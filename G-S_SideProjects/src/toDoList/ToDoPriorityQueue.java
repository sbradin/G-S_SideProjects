package toDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import toDoList.ToDoItem.AssignmentType;

public class ToDoPriorityQueue extends PriorityQueue<ToDoItem> implements ToDoPriorityQueueInterface{

	private Stack<ToDoItem> undoStack;
	public ArrayList<String> classes;
	
	public ToDoPriorityQueue() {
		undoStack = new Stack();
		classes = new ArrayList<String>();
	}
	

	public int getMonthNum(String s) {
		if(s.equals("Jan")) {
			return 1;
		}
		else if(s.equals("Feb")) {
			return 2;
		}
		else if(s.equals("Mar")) {
			return 3;
		}
		else if(s.equals("Apr")) {
			return 4;
		}
		else if(s.equals("May")) {
			return 5;
		}
		else if(s.equals("Jun")) {
			return 6;
		}
		else if(s.equals("Jul")) {
			return 7;
		}
		else if(s.equals("Aug")) {
			return 8;
		}
		else if(s.equals("Sep")) {
			return 9;
		}
		else if(s.equals("Oct")) {
			return 10;
		}
		else if(s.equals("Nov")) {
			return 11;
		}
		else if(s.equals("Dec")) {
			return 12;
		}else {
			return 0;
		}
	}

	@Override
	public boolean move(ToDoItem item, ToDoPriorityQueue missing) {
		if(!this.contains(item)) {
			return false;
		}
		else {
			missing.add(item);
			this.remove(item);
			//write to file stuff
			
			
			return true;
		}
		
	}

	@Override
	public boolean undo() {
		if(this.contains(undoStack.peek())) {
			return false;
		}
		else {
			this.add(undoStack.pop());
			return true;
		}
	}

	@Override
	public boolean delete(ToDoItem item) {
		if(!this.contains(item)) {
			return false;
		}
		else {
			undoStack.add(item);
			this.remove(item);
			//write to file stuff
			return true;
		}
	}
	
	public boolean addTo(ToDoItem item){
		if(!classes.contains(item.thisClass)){
			classes.add(item.thisClass);
		}
		return this.add(item);
	}

}
