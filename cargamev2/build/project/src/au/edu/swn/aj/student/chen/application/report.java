package au.edu.swn.aj.student.chen.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class report {
	
	ArrayList<String> cars= new ArrayList<>();
	ArrayList<String> braketime= new ArrayList<>();
	ArrayList<String> results= new ArrayList<>();
	
	String icon = "icon/audi.png";
	int MainLength = 400;
	int MainWidth = 450;
	
	
    public report(ArrayList<String> cars, ArrayList<String> brakeTime ,ArrayList<String> results){
		
		this.cars=cars;
		this.braketime=brakeTime;
		this.results=results;
		
		
		
		Group myroot = new Group();
		Stage myCarGameStage = new Stage();

		myCarGameStage.setTitle("Games Report");
		myCarGameStage.getIcons().add(new Image(icon));
		myCarGameStage.setScene(new Scene(myroot, MainLength, MainWidth,
				Color.WHITE));
		myCarGameStage.getScene().getStylesheets().add("carGame.css");

		Canvas canvas = new Canvas(MainLength, MainWidth);

		myroot.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();
		
	     TableView table = new TableView();
	     
	     table.setEditable(true);
	     
	        TableColumn carss = new TableColumn("Car");
	        TableColumn timess = new TableColumn("Braking Time");
	        TableColumn resultss = new TableColumn("Good or Bad");
	        
	      
	        
	   
	        ObservableList<rptData> data= FXCollections.observableArrayList();
	        
	        
	        for(int i=0; i<cars.size(); i++){
	        	
	        	data.add(new rptData(cars.get(i),braketime.get(i),results.get(i)));		
	        	
	        	log(braketime.get(i).toString());
	        }
	        
	        carss.setCellValueFactory(
	                new PropertyValueFactory<rptData, String>("carBraked"));
	        timess.setCellValueFactory(
	                new PropertyValueFactory<rptData, String>("brakeTime"));
	        timess.setMinWidth(200);
	        resultss.setCellValueFactory(
	                new PropertyValueFactory<rptData, String>("results"));
	        
	        table.setItems(data);
	        
	        table.getColumns().addAll(carss, timess, resultss);
	        
	        
	        myroot.getChildren().addAll(table);
	        myCarGameStage.show();
		
		
	}
	
	public void log(String output) {
		System.out.println(output);
	}
	

}
