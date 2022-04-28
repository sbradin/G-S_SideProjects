package toDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import toDoList.ToDoItem.AssignmentType;

public class ToDoPriorityQueue extends PriorityQueue<ToDoItem> implements ToDoPriorityQueueInterface{

	private Stack<ToDoItem> undoStack;
	public ToDoPriorityQueue late = new ToDoPriorityQueue();
	
	public ToDoPriorityQueue() {
		undoStack = new Stack();
	}

	public boolean loadFile(String csvFilePath) {
        
        try (Scanner inputFile = new Scanner(new File(csvFilePath))) {
                inputFile.useDelimiter(",");
                if (!inputFile.hasNextLine()) {
                        throw new FileNotFoundException();
                }
                else {
                        while (inputFile.hasNextLine()) {
                        	String ToDoClass = inputFile.next();
                        	String assignmentType = inputFile.next().toUpperCase();
                        	String content = inputFile.next();
                        	String[] calendarSTR = inputFile.next().split(" ");
                        	Calendar cal = Calendar.getInstance();
                        	cal.set(Integer.valueOf(calendarSTR[5]), 
                        			getMonthNum(calendarSTR[1]),
                        			Integer.valueOf(calendarSTR[2]),
                        			Integer.valueOf(calendarSTR[3].split(":")[0]),
                        			Integer.valueOf(calendarSTR[3].split(":")[1]));
                        	ToDoItem newItem = new ToDoItem(ToDoClass, 
                        			AssignmentType.valueOf(assignmentType), 
                        			content, 
                        			cal);
                        	if(newItem.late()) {
                        		late.add(newItem);
                        	}
                        	else {
                        		this.add(newItem);
                        	}
                        	
                            if(inputFile.hasNext()) {
                            	inputFile.nextLine();    
                            }
                        }
                        return true;
                }
        }catch(Exception e) {
        	//make new file with said name instead
            //throw new FileNotFoundException("file is not found");
        	try {
        		File newFile = new File(csvFilePath);
				return newFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	return false;
        	
        }
	}
	private int getMonthNum(String s) {
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
}
