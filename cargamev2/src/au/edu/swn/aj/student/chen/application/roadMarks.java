package au.edu.swn.aj.student.chen.application;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


public class roadMarks {
	double length,angle,x,y;
	Color color;
	
	public roadMarks(double length, double angle, double x,double y, Color color) {
		this.length = length;
		this.angle = angle;
		this.x = x;
		this.y = y;
		this.color=color;

	}
	
	public VBox getMark(){
		
		VBox vbox = new VBox();
		
		Rectangle rec= new Rectangle(5,this.length, this.color);
		rec.getTransforms().add(new Rotate(this.angle,0,0));
		
	    vbox.setLayoutX(this.x);
	    vbox.setLayoutY(this.y);
	    vbox.getChildren().add(rec);
		
		return vbox;
		
		
	}

}
