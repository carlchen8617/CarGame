package au.edu.swn.aj.student.chen.application;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.QuadCurveTo;


public class Roads {

	public  Path buildRoad(int x, int y) {

		Path mypath = new Path();
		//System.out.println("path starting at " + x + " " + y);
		
		 PathElement[] newpath = 
		      {
		         new MoveTo(x,y),
		        // new ArcTo(100, 100, 0, 100, 400, false, false),
				new LineTo(900, 400),
				// new QuadCurveTo(50, 20, 667, 457),
				// new QuadCurveTo(-50,-50, 1800, 300),
				// new LineTo(450,200),
				new ArcTo(200, 100, 0, 200, 400, true, false),

				new ArcTo(200, 100, 0, 900, 400, true, false),
				new LineTo(1200, 200),
				new ArcTo(300, 300, 5, 300, 600, true, false),
				new ArcTo(200, 400, 45, 800, 600, true, false),
				new LineTo(200, 100), new LineTo(400, 900),
		         
		         //new ArcTo(100, 100, 0, 300, 0, false, false),
		         ////new LineTo(100, 0),
		         //new ArcTo(100, 100, 0, 0, 100, false, false),
		         //new LineTo(0, 300),
		         //new ClosePath()
		      };
		 
		  
	     
	     mypath.setStroke(Color.DIMGREY);
	     mypath.setStrokeWidth(75);
	     mypath.getElements().addAll(newpath);
		
		return mypath;
	}

}
