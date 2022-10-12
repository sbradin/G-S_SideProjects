package toDoList;

public interface ToDoListInterface {
    public boolean loadFile(String csvFilePath);
    public String getClassColor(ToDoItem item);
    public boolean late();
    
}
