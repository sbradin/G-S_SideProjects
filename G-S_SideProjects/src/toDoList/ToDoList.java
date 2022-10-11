package toDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

import toDoList.ToDoItem.AssignmentType;

public class ToDoList implements ToDoListInterface{

    public ToDoPriorityQueue currToDo;
    public ToDoPriorityQueue late;
    private HashMap classList = new HashMap();
	
    public ToDoList(){
        currToDo = new ToDoPriorityQueue();
        late = new ToDoPriorityQueue();
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
                        			currToDo.getMonthNum(calendarSTR[1]),
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
                        		currToDo.add(newItem);
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
	public String getClassColor(ToDoItem item) {
		// TODO Auto-generated method stub
		String temp = item.thisClass;

		
		return null;
	}

    @Override
    public boolean late() {
        // TODO Auto-generated method stub
        return false;
    }
}
