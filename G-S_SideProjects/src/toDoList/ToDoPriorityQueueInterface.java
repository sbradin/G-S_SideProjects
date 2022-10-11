package toDoList;

import java.io.File;

public interface ToDoPriorityQueueInterface{
	public boolean move(ToDoItem item, ToDoPriorityQueue missing);
	public boolean undo();
	public boolean delete(ToDoItem item);
	
}
