//*******************************************************
//*  Utility class to help setup threads
/*
********************************************************/

package au.edu.swn.aj.student.chen.application;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.scene.image.ImageView;

public class runcar implements Callable {

	String CarColor;
	int ciruitNumber, startX, startY;
	long speed;

	public runcar(String CarColor, int ciruitNumber, int startX, int startY, long speed ) {
		this.CarColor = CarColor;
		this.ciruitNumber = ciruitNumber;
		this.startX = startX;
		this.startY = startY;
		this.speed=speed;

	}

	public cars call() {
		

		return getCar();
	}

	public cars getCar() {

		cars mycar = new cars(this.CarColor, this.ciruitNumber, this.startX,
				this.startY, this.speed);
		return mycar;

	}
	


}
