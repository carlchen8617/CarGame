package au.edu.swn.aj.student.chen.application;

import java.util.concurrent.Callable;

public class runTrafficLights implements Callable{
	
int xLocation,yLocation;
	
	public runTrafficLights(int xLocation, int yLocation){
		
		this.xLocation=xLocation;
		this.yLocation=yLocation;
		
	}
	
	public trafficLights call() {
		//System.out.println("load traffic ligts!");

		return getTrafficLights();
	}

	public trafficLights getTrafficLights() {

		trafficLights mylights = new trafficLights( this.xLocation,
				this.yLocation);
		return mylights;

	}


}
