package au.edu.swn.aj.student.chen.application;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class trafficLights {

	int xLocation, yLocation;
	int lock = 0; // controls light coodination
	Circle yellowCircle = new Circle(5.0f, Color.GOLD);
	Circle redCircle = new Circle(5.0f, Color.RED);
	Circle greenCircle = new Circle(5.0f, Color.GREEN);

	public trafficLights(int xLocation, int yLocation) {

		this.xLocation = xLocation;
		this.yLocation = yLocation;

	}

	public Circle setRedLight() {

		redCircle.setCenterX(this.xLocation);
		redCircle.setCenterY(this.yLocation);
		redCircle.setVisible(false);

		return redCircle;
	}

	public Circle setYellowLight() {

		yellowCircle.setCenterX(this.xLocation);
		yellowCircle.setCenterY(this.yLocation+10);
		yellowCircle.setVisible(false);

		return yellowCircle;
	}

	public Circle setGreenLight() {

		greenCircle.setCenterX(this.xLocation);
		greenCircle.setCenterY(this.yLocation+20);
		greenCircle.setVisible(false);
		return greenCircle;
	}

	public void switching(int timeToStart) {
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				
				try {
					Thread.sleep(timeToStart);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				greenCircle.setVisible(false);
				yellowCircle.setVisible(false);
				redCircle.setVisible(true);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				greenCircle.setVisible(false);
				yellowCircle.setVisible(true);
				redCircle.setVisible(false);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				greenCircle.setVisible(true);
				yellowCircle.setVisible(false);
				redCircle.setVisible(false);

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				greenCircle.setVisible(false);
				yellowCircle.setVisible(true);
				redCircle.setVisible(false);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}, 10000, 10000);

	}

}
