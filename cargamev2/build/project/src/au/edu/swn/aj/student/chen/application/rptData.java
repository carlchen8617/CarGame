package au.edu.swn.aj.student.chen.application;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class rptData {
    private final SimpleStringProperty carBraked;
    private final SimpleStringProperty  brakeTime;
    private final SimpleStringProperty results;
 
    public rptData(String car, String bt, String rst) {
        this.carBraked = new SimpleStringProperty(car);
        this.brakeTime=  new SimpleStringProperty(bt);
        this.results = new SimpleStringProperty(rst);
    }
 
    public String getCarBraked() {
        return carBraked.get();
    }
    public void setCarBraked(String car) {
    	carBraked.set(car);
    }
        
    public String getBrakeTime() {
    	
    	
    	return brakeTime.get();
        
    }
    public void setBrakeTime(String bt) {
    	brakeTime.set(bt);
    	
    }
    
    public String getResults() {
        return results.get();
    }
    public void setResults(String rst) {
    	results.set(rst);
    }
        
    
    public void log(String output) {
		System.out.println(output);
	}
}
