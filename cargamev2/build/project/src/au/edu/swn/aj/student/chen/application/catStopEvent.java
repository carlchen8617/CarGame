package au.edu.swn.aj.student.chen.application;

import javafx.event.Event;
import javafx.event.EventType;


public class catStopEvent extends Event {
	
	double x, y;
	int car;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final EventType<catStopEvent> CARSTOP = new EventType<>(ANY, "CARSTOP");

	public catStopEvent() {
		super(CARSTOP);
		// TODO Auto-generated constructor stub
	}

	public catStopEvent(double x, double y, int car) {
		super(CARSTOP);
		this.x=x;
		this.y=y;
		this.car=car;
		
		// TODO Auto-generated constructor stub
	}
	
	 public double getX() {
	        return  this.x;
	    }
	 
	 public double getY() {
	       return  this.y;
	    }
	 
	 public int getCar() {
	       return  this.car;
	    }
}
