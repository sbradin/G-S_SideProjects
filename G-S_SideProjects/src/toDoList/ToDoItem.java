package toDoList;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class ToDoItem implements ToDoItemInterface{
	String thisClass;
	AssignmentType thisAssignmentType;
	String thisContent;
	Calendar thisDueDate;
	int numDayInMonth;

	enum AssignmentType{
		HOMEWORK,
		PROJECT,
		EXAM,
		CHORE
	}

	public ToDoItem(String Class, AssignmentType assignmentType, String content, Calendar dueDate){
		thisClass = Class;
		thisAssignmentType = assignmentType; // maybe change to enum (more likely than class)
		thisContent = content;
		thisDueDate = dueDate;
	}

	@Override
	public String getItemClass() {
		// TODO Auto-generated method stub
		return thisClass;
	}

	@Override
	public AssignmentType getAssignmentType() {
		// TODO Auto-generated method stub
		return thisAssignmentType;
	}

	@Override
	public String getItemContent() {
		// TODO Auto-generated method stub
		return thisContent;
	}

	@Override
	public Calendar getDueDate() {
		// TODO Auto-generated method stub
		return thisDueDate;
	}

	@Override
	public boolean late() {
		// TODO Auto-generated method stub
		//due date has not passed
		if(calendarCompareTo(Calendar.getInstance()) > 0) {
			return false;
		}
		//due date has passed
		else {
			return true;
		}

	}


	//have to create our own for Calendar since it only does it based on the milliseconds of instantiation
	@Override
	public long calendarCompareTo(Calendar now) {
		// TODO Auto-generated method stub
		String[] nowSTR = now.getTime().toString().split(" ");
		String[] dueDateSTR = thisDueDate.getTime().toString().split(" ");
		//1 = calendar
		//2 = calendar being compared
		//year
		long daysBetween = ChronoUnit.DAYS.between(thisDueDate.toInstant(), now.toInstant());
		//due date has not passed(1 comes after 2, 2>1)
		if(daysBetween != 0) {
			return daysBetween;
		}
		else {
			//hour
			String[] nowTimeSTR = nowSTR[3].split(":");
			String[] dueDateTimeSTR = dueDateSTR[3].split(":");
			int nowHour = Integer.valueOf(nowTimeSTR[0]);
			int dueHour = Integer.valueOf(dueDateTimeSTR[0]);
			//due date has not passed(1 comes after 2, 2>1)
			if(nowHour < dueHour) {
				return -1;
			}
			//due date has passed(2 comes after 1, 2>1)
			else if(nowHour > dueHour) {
				return 1;
			}
			else {
				int nowMin = Integer.valueOf(nowTimeSTR[1]);
				int dueMin = Integer.valueOf(dueDateTimeSTR[1]);
				//due date has not passed(1 comes after 2, 2>1)
				if(nowMin < dueMin) {
					return -1;
				}
				//due date has passed(2 comes after 1, 2>1)
				else if(nowMin > dueMin) {
					return 1;
				}
				else {
					int nowSec = Integer.valueOf(nowTimeSTR[2]);
					int dueSec = Integer.valueOf(dueDateTimeSTR[2]);
					//due date has not passed(1 comes after 2, 2>1)
					if(nowSec < dueSec) {
						return -1;
					}
					//due date has passed(2 comes after 1, 2>1)
					else if(nowSec > dueSec) {
						return 1;
					}
					//dates are the exact same
					else {
						return 0;
					}
				}
			}
		}
}


@Override
public int compareTo(ToDoItem item) {
	// TODO Auto-generated method stub
	switch(this.thisAssignmentType){
	case HOMEWORK:
		switch(item.thisAssignmentType) {
		case HOMEWORK:
			if(this.calendarCompareTo(item.thisDueDate) < 0){
				return -1;
			}
			else {
				return 1;
			}

		case PROJECT:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			//have the project assignment have more priority than a homework as long as it is due at most 7 days after the project is due
			else if(this.calendarCompareTo(item.thisDueDate) < -7) {
				return -1;
			}
			else {
				return 1;
			}

		case EXAM:
			//if due at the same time, exam takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			//have the homework assignment have more priority than the exam as long as it is 1 days before the exam
			else if(this.calendarCompareTo(item.thisDueDate) < -1) {
				return -1;
			}
			else {
				return 1;
			}

		case CHORE:
			//if due at the same time, homework takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			// have the chore have more priority than the homework assignment as long as the chore comes before the homework assignment by a day
			else if(this.calendarCompareTo(item.thisDueDate) < 1) {
				return -1;
			}
			else {
				return 1;
			}



		}
	case PROJECT:
		switch(item.thisAssignmentType) {
		case HOMEWORK:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			//homework assignment to have more priority than the project as long as the homework comes before the project by 7 days
			else if(this.calendarCompareTo(item.thisDueDate) < 7) {
				return -1;
			}
			else {
				return 1;
			}

		case PROJECT:	
			if(this.calendarCompareTo(item.thisDueDate) < 0){
				return -1;
			}
			else {
				return 1;
			}

		case EXAM:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			// exam will have more priority over the project as long as the exam comes before the project by 3 days
			else if(this.calendarCompareTo(item.thisDueDate) < 3) {
				return -1;
			}	
			else {
				return 1;
			}


		case CHORE:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			//chore will have priority over the project as long as the chore comes before the project by 2 days
			else if(this.calendarCompareTo(item.thisDueDate) < 2) {
				return -1;
			}
			else {
				return 1;
			}

		}
	case EXAM:
		switch(item.thisAssignmentType) {
		case HOMEWORK:
			//if due at the same time, exam takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			// homework will have priority over an exam as long as the homework assignment comes before the exam by 1 day
			else if(this.calendarCompareTo(item.thisDueDate) < 1) {
				return -1;
			}
			else {
				return 1;
			}


		case PROJECT:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			// project will have priority over an exam as long as the project comes after the exam by 3 days, or before at all
			else if(this.calendarCompareTo(item.thisDueDate) < -3) {
				return -1;
			}
			else {
				return 1;
			}


		case EXAM:
			if(this.calendarCompareTo(item.thisDueDate) < 0){
				return -1;
			}
			else {
				return 1;
			}

		case CHORE:
			//if due at the same time, exam takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return 1;
			}
			// chore will have priority over an exam as long as the chore comes before 2 days
			else if(this.calendarCompareTo(item.thisDueDate) < 2) {
				return -1;
			}
			else {
				return 1;
			}

		}
	case CHORE:
		switch(item.thisAssignmentType) {
		case HOMEWORK:
			//if due at the same time, homework takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			//homework will have priority over a chore as long as the homework comes after 1 day
			else if(this.calendarCompareTo(item.thisDueDate) < -1) {
				return -1;
			}
			else {
				return 1;
			}


		case PROJECT:
			//if due at the same time, project takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			//project will have priority over a chore as long as the project comes after 2 days
			else if(this.calendarCompareTo(item.thisDueDate) < -2) {
				return -1;
			}
			else {
				return 1;
			}


		case EXAM:
			//if due at the same time, exam takes priority
			if(this.calendarCompareTo(item.thisDueDate) == 0) {
				return -1;
			}
			// exam will have priority over a chore as lone as the exam comes after 2 days
			else if(this.calendarCompareTo(item.thisDueDate) < -2) {
				return -1;
			}
			else {
				return 1;
			}


		case CHORE:
			if(this.calendarCompareTo(item.thisDueDate) < 0){
				return -1;
			}
			else {
				return 1;
			}
		}


	}



	return 0;
}




}
