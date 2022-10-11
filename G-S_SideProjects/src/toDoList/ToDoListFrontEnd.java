package toDoList;

import java.time.Instant;
import java.util.Calendar;

public class ToDoListFrontEnd {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	
		Calendar cal0 = Calendar.getInstance();
		cal0.set(2022, 11, 31);
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2022, 0, 1);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2022, 0, 1);
		Calendar cal3 = Calendar.getInstance();
		cal3.set(2022, 0, 1);
		Calendar cal4 = Calendar.getInstance();
		cal4.set(2022, 0, 1);
		Calendar cal5 = Calendar.getInstance();
		cal5.set(2022, 5, 1);
		Calendar cal6 = Calendar.getInstance();
		cal6.set(2022, 6, 1);
		Calendar cal7 = Calendar.getInstance();
		cal7.set(2022, 7, 1);
		Calendar cal8 = Calendar.getInstance();
		cal8.set(2022, 8, 1);
		Calendar cal9 = Calendar.getInstance();
		cal9.set(2022, 9, 1);
		Calendar cal10 = Calendar.getInstance();
		cal10.set(2022, 10, 1);
		Calendar cal11 = Calendar.getInstance();
		cal11.set(2022, 11, 1);
		
		ToDoItem item0 = new ToDoItem("Math", ToDoItem.AssignmentType.HOMEWORK, "1st priority", cal0);
		ToDoItem item1 = new ToDoItem("Math", ToDoItem.AssignmentType.PROJECT, "2nd priority", cal1);
		ToDoItem item2 = new ToDoItem("Math", ToDoItem.AssignmentType.EXAM, "3rd priority", cal2);
		ToDoItem item3 = new ToDoItem("Math", ToDoItem.AssignmentType.CHORE, "4th priority", cal3);
		ToDoItem item4 = new ToDoItem("Math", ToDoItem.AssignmentType.HOMEWORK, "5th priority", cal4);
	
		
		ToDoPriorityQueue queue = new ToDoPriorityQueue();
		queue.add(item0);
		queue.add(item1);
		queue.add(item2);
		queue.add(item3);
		queue.add(item4);
		
		while(!queue.isEmpty()) {
			System.out.println(queue.remove().thisContent);
		}
		
		
		System.out.println(cal0.getTime());
		System.out.println(cal1.getTime());
		System.out.println(cal2.getTime());
		System.out.println(cal3.getTime());
		System.out.println(cal4.getTime());
//		System.out.println(cal5.getTime());
//		System.out.println(cal6.getTime());
//		System.out.println(cal7.getTime());
//		System.out.println(cal8.getTime());
//		System.out.println(cal9.getTime());
//		System.out.println(cal10.getTime());
//		System.out.println(cal11.getTime());
		
		
	}

}
