package toDoList;

import java.util.Calendar;

import toDoList.ToDoItem.AssignmentType;

public interface ToDoItemInterface extends Comparable<ToDoItem>{
	public String getItemClass();
	public AssignmentType getAssignmentType();
	public String getItemContent();
	public Calendar getDueDate();
	public boolean late();
	public int compareTo(ToDoItem item);
	public long calendarCompareTo( Calendar now);

}
