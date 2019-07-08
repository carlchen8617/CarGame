//**************************************************
//  
//   The car class is the core of this game
//
//
//***************************************************

package au.edu.swn.aj.student.chen.application;

import javafx.animation.PathTransition;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.shape.Path;

public class cars {

	ImageView iv1 = new ImageView();
	String CarColor;
	int ciruitNumber, startX, startY;
    long speed;
    
	public cars(String CarColor, int ciruitNumber, int startX, int startY, long speed) {
		this.CarColor = CarColor;
		this.ciruitNumber = ciruitNumber;
		this.startX = startX;
		this.startY = startY;
		this.speed=speed;

	}

	Path path = new Path();

	PathTransition pathTransition = new PathTransition();
	PathTransition spx = new PathTransition();

	public PathTransition getPathTransition() {

		return spx;

	}

	int toggle = 0;// get start and stop status
	String imageLocation = "images/";

	private ImageView buildCars(String CarColor, int ciruitNumber, int startX,
			int startY) {

		Image car = new Image(imageLocation + CarColor + ".png");
		// System.out.println(imageLocation + CarColor + ".png");

		iv1.setImage(car);

		iv1.setScaleX(.15);
		iv1.setScaleY(.15);
		iv1.setPickOnBounds(true); // allows click on transparent areas

		// path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
		// path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
		// paths sp = new paths();
		// spx = sp.setPath(iv1, 0, startX, startY);

		return iv1;

	}

	public ImageView runCars(int startTime) throws InterruptedException {

		ImageView cary = buildCars(CarColor, ciruitNumber, startX, startY);

		// System.out.println("runcarcar is running!");
		Thread.sleep(startTime);
		paths sp = new paths();
		spx = sp.setPath(cary, 0, startX, startY,this.speed );
		System.out.println(this.speed);

		return cary;

	}

}
