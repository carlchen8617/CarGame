package au.edu.swn.aj.student.chen.application;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import au.edu.swn.aj.student.chen.application.catStopEvent;
import au.edu.swn.aj.student.chen.application.cars;
import au.edu.swn.aj.student.chen.utils.*;

public class runEngine {

	String Maintitle = "Traffic Safety Land";
	String icon = "icon/audi.png";
	int MainLength = 1750;
	int MainWidth = 700;
	int toggleA, toggleB, toggleC = 0; // switches for start and stop
	/*
	 * variables dealing with controlling car stop and start movements
	 */

	int secndCarStop, thrdCarStop; // indicate if the cars are stopping first
									// time

	long secndCarLastStopTime, secndCarLastStartTime, secndCarCrtStopTime;
	long thrdCarLastStopTime, thrdCarLastStartTime, thrdCarCrtStopTime;

	/*
	 * end variables dealing with controlling car stop and start movements
	 */
	long startTime;// game start time
	long stopTime; // anycar clicked stop time
	int roundAlready = 0; // test if the front car has run a full loop
	int stopper = 30;
	Future<cars> CarA, CarB, CarC;
	ScheduledFuture SF, SFA, SFC; // these are the scheduledPool threads for the
									// cars
	boolean SFon, SFAon, SFCon; // to indicate if the scheduledPool threads are
								// running
	int SFH, SFAH, SFCH; //handles to check at close program
	
	long speed;

	/*
	 * report section
	 */
	ArrayList<String> CarBreaks = new ArrayList<>();
	ArrayList<String> CarClickedTime = new ArrayList<>();
	ArrayList<String> RuleViolated = new ArrayList<>();

	public runEngine(ArrayList<String> cars, String speed)
			throws InterruptedException, ExecutionException {

		log(speed);
		/*
		 * setup stage
		 */
		Group myroot = new Group();

		final RadialGradient radialGradient = new RadialGradient(0, 0, 1600,
				800, 1800, false, CycleMethod.NO_CYCLE,
				new Stop(0, Color.BLACK), new Stop(1, Color.DARKGREEN));

		Stage myCarGameStage = new Stage();

		myCarGameStage.setTitle(Maintitle);

		myCarGameStage.getIcons().add(new Image(icon));
		myCarGameStage.setScene(new Scene(myroot, MainLength, MainWidth,
				radialGradient));
		myCarGameStage.getScene().getStylesheets().add("carGame.css");

		Canvas canvas = new Canvas(MainLength, MainWidth);

		myroot.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		/*
		 * lay roads
		 */

		Roads firstAve = new Roads();
		Path road1 = firstAve.buildRoad(1800, 400);

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(50);

		/*
		 * 
		 * lay road marks
		 */

		VBox stopline1 = new roadMarks(75, 45, 880, 285, Color.WHITE).getMark();
		VBox giveWayline2 = new roadMarks(95, -50, 390, 210, Color.SLATEGRAY)
				.getMark();
		VBox giveWayline3 = new roadMarks(80, -50, 290, 130, Color.SLATEGRAY)
				.getMark();

		// **********************************************************
		//
		// loads traffic lights
		//
		//
		// ***********************************************************

		// traffic light frame

		Rectangle trafficLightFrame1 = new Rectangle(842, 520, 15, 40);
		Rectangle trafficLightFrame2 = new Rectangle(242, 420, 15, 40);
		Rectangle trafficLightFrame3 = new Rectangle(870, 250, 15, 40);

		trafficLightFrame1.setFill(Color.BLACK);
		trafficLightFrame1.setVisible(true);
		trafficLightFrame2.setFill(Color.BLACK);
		trafficLightFrame2.setVisible(true);
		trafficLightFrame3.setFill(Color.BLACK);
		trafficLightFrame3.setVisible(true);

		/*
		 * traffic light threads using traffic light object, each executor call
		 * puts one set of traffic lights(r,g,y) at different location
		 */

		Future<trafficLights> tr1 = executor.submit(new runTrafficLights(850,
				530));
		Future<trafficLights> tr2 = executor.submit(new runTrafficLights(250,
				430));
		Future<trafficLights> tr3 = executor.submit(new runTrafficLights(878,
				260));

		// run 1st traffic lights

		Circle myred1 = tr1.get().setRedLight();
		Circle myyellow1 = tr1.get().setYellowLight();
		Circle mygreen1 = tr1.get().setGreenLight();

		Runnable r1 = new Runnable() {
			public void run() {
				try {
					tr1.get().switching(7700);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		r1.run();

		// run 2nd traffic lights

		Circle myred2 = tr2.get().setRedLight();
		Circle myyellow2 = tr2.get().setYellowLight();
		Circle mygreen2 = tr2.get().setGreenLight();

		Runnable r = new Runnable() {
			public void run() {
				try {
					tr2.get().switching(4000);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		r.run();

		// run 3rd traffic lights

		Circle myred3 = tr3.get().setRedLight();
		Circle myyellow3 = tr3.get().setYellowLight();
		Circle mygreen3 = tr3.get().setGreenLight();
		Runnable r3 = new Runnable() {
			public void run() {
				try {
					tr3.get().switching(700);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		r3.run();

		// **********************************************************
		//
		// run clock
		//
		//
		// ***********************************************************

		Text gameClock = new Text("Game starting");

		gameClock.setX(50);
		gameClock.setY(50);
		// gameClock.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		gameClock.setId("fancytext");
		gameClock.setFill(Color.RED);

		Runnable clock = new Runnable() {
			public void run() {
				startTime = System.currentTimeMillis();
				runClock(gameClock, startTime);
			}
		};

		clock.run();

		// **********************************************************
		//
		// play animation.
		// This section use java concurrency API aka future:)
		//
		// ***********************************************************

		// List<ImageView> resultList = new ArrayList<>(); //if there are a lot
		// threads then use lists

		/*
		 * gap Control initialization
		 */

		// set up speed

		if (speed.trim().contains("50")) {
			this.speed = 60000;
			log("4" + this.speed);
		}
		if (speed.trim().contains("40")) {
			this.speed = 70000;
			log("5" + this.speed);
		}
		if (speed.trim().contains("60")) {
			this.speed = 40000;
			log("6" + this.speed);
		}

		HashMap<String, Integer> gapControl = new HashMap<>();

		CarA = executor
				.submit(new runcar(cars.get(0), 0, 1500, 400, this.speed));
		CarB = executor
				.submit(new runcar(cars.get(1), 0, 1500, 400, this.speed));

		CarC = executor
				.submit(new runcar(cars.get(2), 0, 1500, 400, this.speed));

		ImageView CarAinst = CarA.get().runCars(5);

		ImageView CarBinst = CarB.get().runCars(1800);

		ImageView CarCinst = CarC.get().runCars(1900);

		gapControl.put("CarB-CarC-CarA", 57000);
		gapControl.put("CarA-CarB", 1300);
		gapControl.put("CarA-CarB-CarC", 2400);
		gapControl.put("CarB-CarC", 1150);

		gapCalcu gapcalcu = new gapCalcu();

		EventTarget target = new EventTarget() {

			@Override
			public EventDispatchChain buildEventDispatchChain(
					EventDispatchChain tail) {
				return tail.append(new EventDispatcher() {

					@Override
					public Event dispatchEvent(Event event,
							EventDispatchChain tail) {

						int whichCar = ((catStopEvent) event).getCar();

						if (whichCar == 2) {

							/*
							 * If CarB stopped
							 */

							// CarCinst.localToParent(CarCinst.getTranslateX(),
							// CarCinst.getTranslateY());
							
						
							
							/*
							 *  start calculating
							 * 
							 */

							double xx = CarCinst.getTranslateX()
									- CarAinst.getTranslateX(); // check carA
																// and CarC
																// distance
							double yy = CarCinst.getTranslateY()
									- CarAinst.getTranslateY();

							double distance = Math.sqrt(Math.pow(xx, 2)
									+ Math.pow(yy, 2));

							double xxc = ((catStopEvent) event).getX()
									- CarCinst.getTranslateX(); // check carB
																// and CarC
																// distance
							double yyc = ((catStopEvent) event).getY()
									- CarCinst.getTranslateY();

							double distance2 = Math.sqrt(Math.pow(xxc, 2)
									+ Math.pow(yyc, 2));

							double xxcc = ((catStopEvent) event).getX()
									- CarAinst.getTranslateX(); // check carB
																// and CarA
																// distance
							double yycc = ((catStopEvent) event).getY()
									- CarAinst.getTranslateY();

							double distance3 = Math.sqrt(Math.pow(xxcc, 2)
									+ Math.pow(yycc, 2));

							log("C and A" + distance);
							log("C and B" + distance2);
							log("B and A" + distance3);

							if (distance < 70) {

								try {
									CarA.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (distance2 < 70) {

								try {
									CarC.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (distance3 < 70) {

								try {
									CarA.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}

						else if (whichCar == 1) {

							/*
							 * If CarA stopped
							 */

							

							/*
							 *  start calculating
							 * 
							 */

							double xxBC = CarBinst.getTranslateX() //carB and CarC
									- CarCinst.getTranslateX();
							double yyBC = CarBinst.getTranslateY()
									- CarCinst.getTranslateY();

							double distanceBC = Math.sqrt(Math.pow(xxBC, 2)
									+ Math.pow(yyBC, 2));

							double xxAB = ((catStopEvent) event).getX() //carA and carB
									- CarBinst.getTranslateX();
							double yyAB = ((catStopEvent) event).getY()
									- CarBinst.getTranslateY();

							double distanceABA = Math.sqrt(Math.pow(xxAB, 2)
									+ Math.pow(yyAB, 2));
							
							double xxAC = ((catStopEvent) event).getX() //carA and carC
									- CarCinst.getTranslateX();
							double yyAC = ((catStopEvent) event).getY()
									- CarCinst.getTranslateY();

							double distanceABC = Math.sqrt(Math.pow(xxAC, 2)
									+ Math.pow(yyAC, 2));

							log("C and A" + distanceABC);
							log("C and B" + distanceBC);
							log("B and A" + distanceABA);

							if (distanceBC < 70) {

								try {
									CarC.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (distanceABA < 70) {

								try {
									CarB.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							if (distanceABC < 70) {

								try {
									CarC.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} else if (whichCar == 3) {
							/*
							 * If CarC stopped
							 */

							

							/*
							 *  start calculating
							 * 
							 */

							double xxAB = CarBinst.getTranslateX() //carB and carA
									- CarAinst.getTranslateX();
							double yyAB = CarBinst.getTranslateY()
									- CarAinst.getTranslateY();

							double distanceAB = Math.sqrt(Math.pow(xxAB, 2)
									+ Math.pow(yyAB, 2));

							double xxAC = ((catStopEvent) event).getX() //carA and carC
									- CarAinst.getTranslateX();
							double yyAC = ((catStopEvent) event).getY()
									- CarAinst.getTranslateY();

							double distanceAC = Math.sqrt(Math.pow(xxAC, 2)
									+ Math.pow(yyAC, 2));
							
							double xxACB = ((catStopEvent) event).getX() //carB and carC
									- CarBinst.getTranslateX();
							double yyACB = ((catStopEvent) event).getY()
									- CarBinst.getTranslateY();

							double distanceACB = Math.sqrt(Math.pow(xxACB, 2)
									+ Math.pow(yyACB, 2));

							log("C and A" + distanceAC);
							log("C and B" + distanceACB);
							log("B and A" + distanceAB);

							if (distanceAB < 70) {

								try {
									CarB.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (distanceAC < 70) {

								try {
									CarA.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							if (distanceACB < 70) {

								try {
									CarA.get().getPathTransition().pause();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}

						tail.dispatchEvent(event);
						return event;
					}
				});
			}
		};

		/*
		 * 
		 * handles brake clicks
		 */

		ScheduledExecutorService scheduledPool = Executors
				.newScheduledThreadPool(3);

		CarAinst.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				toggleA++;// odd to stop, even to start

				if (toggleA % 2 == 1) {

					try {
						
						/*
						 * write report
						 * 
						 */
						CarBreaks.add("CarA");
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");  
						CarClickedTime.add(sdf.format( new Date(System.currentTimeMillis())));
						RuleViolated.add("undefined");
						

						CarA.get().getPathTransition().pause();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {

						Runnable runnabledelayedTask = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								double carAX = CarAinst.getTranslateX();
								double carAY = CarAinst.getTranslateY();
								Event catStopEvent = new catStopEvent(carAX,
										carAY, 1);
								Event.fireEvent(target, catStopEvent);
							}

						};

						SFA = scheduledPool.scheduleAtFixedRate(
								runnabledelayedTask, 200, 200,
								TimeUnit.MILLISECONDS);
						SFAon = true;
						SFAH++;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					System.out.println("start");
					SFA.cancel(true);
					SFAon = false;
					SFAH++;
					try {
						CarA.get().getPathTransition().play();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {

						delayedStart(CarB, gapcalcu.getStartTime(1,
								System.currentTimeMillis(), 0L,
								gapControl.get("CarA-CarB")));

						delayedStart(CarC, gapcalcu.getStartTime(1,
								System.currentTimeMillis(), 0L,
								gapControl.get("CarA-CarB-CarC")));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

		CarBinst.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				toggleB++;

				if (toggleB % 2 == 1) {

					try {
						
						/*
						 * write report
						 * 
						 */
						CarBreaks.add("CarB");
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");  
						CarClickedTime.add(sdf.format( new Date(System.currentTimeMillis())));
						RuleViolated.add("undefined");
						
						CarB.get().getPathTransition().pause();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						/*
						 * CarC
						 * 
						 * 
						 * 
						 * 
						 * 
						 * delayedStop( CarC, gapcalcu.getStopTime(2, 0L, 0L,
						 * System.currentTimeMillis(),
						 * gapControl.get("CarB-CarC"), 0));
						 */

						/*
						 * CarA
						 */

						Runnable runnabledelayedTask = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								double carX = CarBinst.getTranslateX();
								double carY = CarBinst.getTranslateY();
								Event catStopEvent = new catStopEvent(carX,
										carY, 2);
								Event.fireEvent(target, catStopEvent);
							}

						};

						SF = scheduledPool.scheduleAtFixedRate(
								runnabledelayedTask, 200, 200,
								TimeUnit.MILLISECONDS);
						SFon = true;
						SFH++;
						/*
						 * Concurrency code not being used
						 * 
						 * 
						 * 
						 * 
						 * System.out.println(gapControl.get("CarB-CarC-CarA"));
						 * 
						 * 
						 * if (secndCarStop == 0) {
						 * 
						 * secndCarLastStopTime = startTime;
						 * 
						 * }
						 * 
						 * 
						 * long secndCarCrtStopTime =
						 * System.currentTimeMillis()-1200;
						 * 
						 * int repint = gapcalcu.getStopTime(2,
						 * secndCarLastStopTime, secndCarLastStartTime,
						 * secndCarCrtStopTime,
						 * gapControl.get("CarB-CarC-CarA"), secndCarStop);
						 * 
						 * ScheduledExecutorService scheduledPool = Executors
						 * .newSingleThreadScheduledExecutor();
						 * 
						 * 
						 * Runnable runnabledelayedTask = new Runnable()
						 * 
						 * {
						 * 
						 * @Override public void run() {
						 * 
						 * try { CarA.get().getPathTransition() .pause(); }
						 * catch (InterruptedException | ExecutionException e) {
						 * // TODO Auto-generated catch block
						 * e.printStackTrace(); }
						 * 
						 * }
						 * 
						 * };
						 * 
						 * if(SF != null){ SF.cancel(true); }
						 * 
						 * 
						 * SF=scheduledPool.schedule(runnabledelayedTask,
						 * repint, TimeUnit.MILLISECONDS);
						 * 
						 * // delayedStop(CarA,repint,timer );
						 * 
						 * System.out.println("STOP B " + repint);
						 * secndCarLastStopTime = secndCarCrtStopTime;
						 * gapControl.replace("CarB-CarC-CarA", repint);
						 * secndCarStop++;
						 */

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					System.out.println("start");
					SF.cancel(true);
					SFon = false;
					SFH++;
					try {
						CarB.get().getPathTransition().play();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {

						delayedStart(CarC, 1200);
						secndCarLastStartTime = System.currentTimeMillis();
						delayedStart(CarA, 1200);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

		CarCinst.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				toggleC++;

				if (toggleC % 2 == 1) {

					try {
						/*
						 * write report
						 * 
						 */
						CarBreaks.add("CarC");
						
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");  
						CarClickedTime.add(sdf.format( new Date(System.currentTimeMillis())));
					    
						RuleViolated.add("undefined");
						
						
						CarC.get().getPathTransition().pause();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {

						/*
						 * CarA B
						 */

						Runnable runnabledelayedTask = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								double carCX = CarCinst.getTranslateX();
								double carCY = CarCinst.getTranslateY();
								Event catStopEvent = new catStopEvent(carCX,
										carCY, 3);
								Event.fireEvent(target, catStopEvent);
							}

						};

						SFC = scheduledPool.scheduleAtFixedRate(
								runnabledelayedTask, 200, 200,
								TimeUnit.MILLISECONDS);
						SFCon = true;
						SFCH++;

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					System.out.println("start CarC");
					SFC.cancel(true);
					SFCon = false;
					SFCH++;

					try {
						CarC.get().getPathTransition().play();
					} catch (InterruptedException | ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {

						delayedStart(CarA, 1200);
						secndCarLastStartTime = System.currentTimeMillis();
						delayedStart(CarB, 1200);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

		myCarGameStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.out
						.println("Stage is closing. shutting down threads.......");
				if (SFCon && !(SFC.isCancelled())) {
					SFC.cancel(true);
				}
				if (SFon && !SF.isCancelled()) {
					SF.cancel(true);
				}

				if (SFAon && !SFA.isCancelled()) {
					SFA.cancel(true);
				}
				
				@SuppressWarnings("unused")
				report rpt=new report(CarBreaks,CarClickedTime,RuleViolated);
			}
		});

		// The list gets all objects and put them on the stage

		List<Node> list = new ArrayList<Node>();

		list.add(road1);
		list.add(stopline1);
		list.add(giveWayline2);
		list.add(giveWayline3);
		list.add(CarAinst);
		list.add(CarBinst);
		list.add(CarCinst);
		list.add(trafficLightFrame1);
		list.add(trafficLightFrame2);
		list.add(trafficLightFrame3);

		list.add(gameClock);
		list.add(myred1);
		list.add(myyellow1);
		list.add(mygreen1);
		list.add(myred2);
		list.add(myyellow2);
		list.add(mygreen2);
		list.add(myred3);
		list.add(myyellow3);
		list.add(mygreen3);

		myroot.getChildren().addAll(list);

		gc.setFill(Color.RED);

		myCarGameStage.show();

	}

	/*
	 * private void delayedStop(Future<cars> Car, int delay) throws Exception {
	 * Timer timers = new Timer();
	 * 
	 * timers.schedule(new TimerTask() {
	 * 
	 * public void run() { try { System.out.println(System.currentTimeMillis());
	 * Car.get().getPathTransition().pause(); } catch (InterruptedException |
	 * ExecutionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * } }, delay);
	 * 
	 * }
	 */
	private void delayedStart(Future<cars> Car, int delay) throws Exception {
		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			public void run() {
				try {
					Car.get().getPathTransition().play();
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, delay);

	}

	private void runClock(Text gameClock, long startTime) { // run program clock
		Timer timer2 = new Timer();

		timer2.scheduleAtFixedRate(new TimerTask() {

			public void run() {

				// value.setValue(String.valueOf(System.currentTimeMillis()));

				gameClock.setText("Game time left: "
						+ String.valueOf((30 - (System.currentTimeMillis() - startTime) / 1000))
						+ " seconds");

				stopper--;

				if (stopper < 0) {
					gameClock.setText("Game Over!");
					
					log("Game Over!");
					timer2.cancel();
					
					try {
						gameOver();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// System.out.println(String.valueOf((System.currentTimeMillis()-startTime)/1000));
			}
		}, 1000, 1000);

	}

	private void gameOver() throws InterruptedException, ExecutionException {// game
																				// stop
	 		if(SFH>0)	{
	 			 SF.cancel(true);
	 		}
	 		
	 		if(SFAH>0)	{
	 			 SFA.cancel(true);
	 		}
	 		
	 		if(SFCH>0)	{
	 			 SFC.cancel(true);
	 		}
         
      
	
		CarA.get().getPathTransition().stop();
	
		CarB.get().getPathTransition().stop();
	
		CarC.get().getPathTransition().stop();
		
		
		
		for(int i =0; i< CarBreaks.size(); i++){
			log(CarBreaks.get(i));
			log(CarClickedTime.get(i));
			log(RuleViolated.get(i));
			
			
			
		}
		log("Close the game window to see your results");
		
		
		
	}
	
	

	public void log(String output) {
		System.out.println(output);
	}

}
