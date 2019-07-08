package test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;
import au.edu.swn.aj.student.chen.application.*;

public class testGame  extends Application{

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub
		Application.launch(args);
		
		
		

	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<String> CarBreaks = new ArrayList<>();
		ArrayList<String> CarClickedTime = new ArrayList<>();
		ArrayList<String> RuleViolated = new ArrayList<>();
		
		
		for(int i=0; i<100; i++){
			CarBreaks.add("Car "+ i );
			
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");  
			CarClickedTime.add(sdf.format( new Date(System.currentTimeMillis())));
			RuleViolated.add("undefined "+ i);
			
		}
		
		
		report rpt=new report(CarBreaks,CarClickedTime,RuleViolated);
		
	}
	
	

}
