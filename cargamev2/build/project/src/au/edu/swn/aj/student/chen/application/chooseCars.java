package au.edu.swn.aj.student.chen.application;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class chooseCars {
	
	
	 ImageView iv1 = new ImageView();
	 String imageLocation = "images/";
	 String car;
	
	public chooseCars(String car) {
		this.car=car;
		
		 
	}

	public CheckBox getBox(){
		
		CheckBox cb=new CheckBox(car);
		
	
		return cb;
	  
		
	}
	
	public ImageView getIV(){
		
		ImageView cariv=new ImageView(imageLocation + car+ ".png");
	    cariv.setScaleX(.15);
	    cariv.setScaleY(.15);
	   return cariv;
	   
	    
	   	
	  
		
	}
	
	
	

		
	
  

}

