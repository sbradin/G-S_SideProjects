package toDoList;

import java.time.Instant;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import toDoList.ToDoItem.AssignmentType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import java.util.Random;
import java.util.Stack;
import java.util.Calendar;

public class ToDoListFrontEnd extends Application{
    
    class Bubble{
        Bubble(ToDoItem item){
            Image bubbleImage = new Image("https://media1.giphy.com/media/Q7rFLFqclXSpeJzHtV/200w.gif?cid=82a1493bhkanwnzrz5i6oi5epge77ltkadfqhrjtx72rlj1c&rid=200w.gif&ct=g");
            ImageView bubbleView = new ImageView(bubbleImage);
            Button bubbleButton = new Button();
            bubbleButton.setGraphic(bubbleView);
            bubbleButton.setText(item.toString());
            bubbles.getChildren().add(bubbleButton);
            
           
            bubbleButton.setOnAction(event ->{
                bubbleView.setImage(new Image(this.getClass().getResource("https://media1.giphy.com/media/Q7rFLFqclXSpeJzHtV/200w.gif?cid=82a1493bhkanwnzrz5i6oi5epge77ltkadfqhrjtx72rlj1c&rid=200w.gif&ct=g").toExternalForm()));
                bubbleButton.setGraphic(bubbleButton);
                queue.delete(item);
                UndoStack.add(item);
                
                
                
            });
        }
    }
	
    public ToDoPriorityQueue queue = new ToDoPriorityQueue();
    public Stack UndoStack = new Stack();
    public FlowPane bubbles = new FlowPane();
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    
	    Application.launch();
		
	
		Calendar cal0 = Calendar.getInstance();
		cal0.set(2022, 11, 31);
		
		ToDoItem item0 = new ToDoItem("Math", ToDoItem.AssignmentType.HOMEWORK, "1st priority", cal0);
		
	
		
		ToDoPriorityQueue queue = new ToDoPriorityQueue();
		queue.add(item0);
	
		
		while(!queue.isEmpty()) {
			System.out.println(queue.remove().thisContent);
		}
		
		
		System.out.println(cal0.getTime());
		
		
	}

    @Override
    public void start(final Stage stage) throws Exception {
        // TODO Auto-generated method stub
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("ToDo List");
       
        
        FlowPane bottomButtons = new FlowPane();
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        
        //late assignments window
        BorderPane latePane = new BorderPane();
        Scene lateScene = new Scene(latePane, 640, 480);
        Button lateButton = new Button("Late Assignments");
        //switch to window with late button
        lateButton.setOnAction(event -> {
            //return to home scene
            Button returnButton = new Button("Return");
            returnButton.setOnAction(event2 -> {
                stage.setScene(scene);
                stage.show();
            });
            latePane.setBottom(returnButton);
            BorderPane.setAlignment(returnButton, Pos.BOTTOM_RIGHT);
            stage.setScene(lateScene);
            stage.show();
        });
        borderPane.setTop(lateButton);
        BorderPane.setAlignment(lateButton, Pos.TOP_RIGHT);
        
        //read from file
        
        borderPane.setCenter(bubbles);
        //bubbles
        for(ToDoItem assignment : queue) {
            Bubble newBubble = new Bubble(assignment);
            
            //set on hover
            //set on click
            
            
        }
        
        
        
        //add assignment button
        BorderPane addPane = new BorderPane();
        Scene addScene = new Scene(addPane, 640, 480);
        Button addButton = new Button("Add Assignment");
        // switch scene to add prompts
        addButton.setOnAction(event -> {
            //return to home scene
            Button returnButton = new Button("Return");
            returnButton.setOnAction(event2 -> {
                stage.setScene(scene);
                stage.show();
            });
            addPane.setBottom(returnButton);
            BorderPane.setAlignment(returnButton, Pos.BOTTOM_RIGHT);
            
            ObservableList<String> options = 
                    FXCollections.observableArrayList(
                        "HOMEWORK",
                        "PROJECT",
                        "EXAM",
                        "CHORE"
                    );
            ComboBox<String> typeComboBox = new ComboBox<String>(options);
           
            TextField classInput = new TextField();
            TextField assignmentContentInput = new TextField();
            TextField dayInput = new TextField();
            TextField monthInput = new TextField();
            TextField yearInput = new TextField();
            TextField timeInput = new TextField();
            VBox inputs = new VBox();
            HBox textBox1 = new HBox();
            Label optionLabel = new Label(" Select ToDo Option: ");
            textBox1.getChildren().addAll(optionLabel, typeComboBox);
            HBox textBox2 = new HBox();
            Label classLabel = new Label(" Enter Class Name: ");
            textBox2.getChildren().addAll(classLabel, classInput);
            HBox textBox3 = new HBox();
            Label assignmentLabel = new Label(" Enter Assignment Content: ");
            textBox3.getChildren().addAll(assignmentLabel, assignmentContentInput);
            HBox textBox4 = new HBox();
            //make new calendar input to get separate sections of the calendar object, set defaults 
            Label dayLabel = new Label(" Enter Due Date Day: ");
            Label monthLabel = new Label(" Enter Due Date Month: ");
            textBox4.getChildren().addAll(dayLabel, dayInput, monthLabel, monthInput);
            HBox textBox5 = new HBox();
            Label yearLabel = new Label(" Enter Due Date Year: ");
            Label timeLabel = new Label(" Enter Due Date Time (hour:minute): ");
            
            textBox5.getChildren().addAll(yearLabel, yearInput, timeLabel, timeInput);
            
            Button submitButton = new Button("Create New ToDo");
            submitButton.setOnAction(event2 ->{
                String className = classInput.getText();
                String assignmentStuff = assignmentContentInput.getText();
                int calendarDay =Integer.valueOf(dayInput.getText());
                int calendarMonth =Integer.valueOf(monthInput.getText());
                int calendarYear =Integer.valueOf(yearInput.getText());
                Calendar newDueDate = Calendar.getInstance();
                if(timeInput.getText() != null && timeInput.getText().contains(":")) {
                    int calendarHour =Integer.valueOf(timeInput.getText().split(":")[0]);
                    int calendarMinute =Integer.valueOf(timeInput.getText().split(":")[1]);
                    newDueDate.set(calendarYear, calendarMonth, calendarDay, calendarHour, calendarMinute, 59 );
                }
                else {
                    newDueDate.set(calendarYear, calendarMonth, calendarDay, 11, 59, 59 );
                }
                
                // make new bubble instance with data
                
                
                
                ToDoItem newItem = new ToDoItem(className, AssignmentType.valueOf(typeComboBox.getValue()),assignmentStuff, newDueDate);
                //add item to queue
                queue.add(newItem);
                
                Bubble newBub = new Bubble(newItem);
                
                //write to file
                stage.setScene(scene);
                stage.show();
            });
            
            
            
            
            inputs.getChildren().addAll(textBox1, textBox2, textBox3, textBox4, textBox5, submitButton);
            addPane.setCenter(inputs);
            //BorderPane.setAlignment(inputs, Pos.BASELINE_LEFT);
            inputs.setAlignment(Pos.CENTER_LEFT);
            stage.setScene(addScene);
            stage.show();
        });
        
        // TODO load existing ToDo items from ToDo csv and add them to scene or late scene
        
       
        bottomButtons.getChildren().add(exitButton);
        bottomButtons.getChildren().add(addButton);
        borderPane.setBottom(bottomButtons);
        BorderPane.setAlignment(bottomButtons, Pos.BOTTOM_LEFT);
         
        stage.setScene(scene);
        stage.show();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
