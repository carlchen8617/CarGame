package au.edu.swn.aj.student.chen.application;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Admin extends Application {

	String Maintitle = "Traffic Safety Land";
	String icon = "file:icon//audi.png";
	String uname = "carl";
	String passw = "letmein";
	ArrayList<String> checkbox = new ArrayList<>();
	ArrayList<CheckBox> cb = new ArrayList<>(10);
	ArrayList<ImageView> iv = new ArrayList<>(10);
	Label chooseCar = new Label("Choose 3 cars");
	Label chooseSpeed = new Label("Choose speed");
	Label chooseCase = new Label("Choose a scenario");
	Button Go = new Button("Go!");
	ImageView ivA=new ImageView();
	
	
	/*
	 * 
	 * final selections
	 * 
	 */
	
	ArrayList<String> carSelection= new ArrayList();
	String path;
	String speed;
	int ok=0;
	

	/*
	 * speed buttons
	 */
	ToggleGroup group = new ToggleGroup();
	RadioButton SL1 = new RadioButton("40km/h");
	RadioButton SL2 = new RadioButton("50km/h");
	RadioButton SL3 = new RadioButton("60km/h");

	/*
	 * Case buttons
	 */

	ToggleGroup group2 = new ToggleGroup();
	RadioButton SN1 = new RadioButton("Scenario 1");
	RadioButton SN2 = new RadioButton("Scenario 2");

	List<Node> list = new ArrayList<Node>();// node list
	List<Node> listA = new ArrayList<Node>();// node list

	VBox carBoxck = new VBox();
	VBox carBoxIV = new VBox();

	int MainLength = 900;
	int MainWidth = 450;
	final int countck = 0;// counters for listing checkboxes
	
    public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		
	
		Application.launch (args);
		
	}
	

	public void start(Stage myAdminStage) {
		
		String icon = "icon/audi.png";
		Group myroota = new Group();
        
		

		myAdminStage.setTitle(Maintitle);
		myAdminStage.getIcons().add(new Image(icon));
		myAdminStage.setScene(new Scene(myroota, MainLength, MainWidth,
				Color.WHITE));
		Image stop = new Image("images/StopSign1.jpg");
		ImagePattern pattern = new ImagePattern(stop);
		Image soneImage=new Image("images/sone.png");
		
		myAdminStage.getScene().setFill(pattern);
		

		myAdminStage.getScene().getStylesheets().add("carGame.css");

		Canvas canvas = new Canvas(MainLength, MainWidth);

		myroota.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		Text scenetitle = new Text("Traffic Safety Land");
		scenetitle.setId("scenetitleAdmin");
		scenetitle.setX(170);
		scenetitle.setY(120);

		Text welcome = new Text("Welcome");
		welcome.setId("welcome");
		welcome.setX(540);
		welcome.setY(160);

		Label userName = new Label("User Name:");
		userName.setId("userName");
		userName.setLayoutX(180);
		userName.setLayoutY(220);

		TextField userTextField = new TextField();
		userTextField.setId("userTextField");
		userTextField.setLayoutX(300);
		userTextField.setLayoutY(220);

		Label pw = new Label("Password:");
		pw.setId("Password");
		pw.setLayoutX(180);
		pw.setLayoutY(260);

		PasswordField pwBox = new PasswordField();
		pwBox.setId("pwBox ");
		pwBox.setLayoutX(300);
		pwBox.setLayoutY(260);

		Button login = new Button("Login");
		login.setId("login ");
		login.setLayoutX(400);
		login.setLayoutY(300);

		Label author = new Label("@Carl Chen Student ID: 101665196");
		// userName.setId("userName");
		author.setLayoutX(600);
		author.setLayoutY(400);

		checkbox.add("beige");
		checkbox.add("blue");
		checkbox.add("green");
		checkbox.add("grey");
		checkbox.add("orange");
		checkbox.add("police");
		checkbox.add("purple");
		checkbox.add("red");
		checkbox.add("sky");
		checkbox.add("white");
		checkbox.add("yellow");

		/*
		 * 
		 * get checkbox
		 */

		for (int i = 0; i < checkbox.size(); i++) {
			cb.add(new chooseCars(checkbox.get(i)).getBox());
			carBoxck.getChildren().add(cb.get(i));
			carBoxck.setLayoutX(50);
			carBoxck.setLayoutY(50);
			System.out.println(cb.get(i).getText());
		}

		/*
		 * 
		 * get carlisted
		 */

		for (int i = 0; i < checkbox.size(); i++) {
			iv.add(new chooseCars(checkbox.get(i)).getIV());
			iv.get(i).setVisible(false);
			listA.add(iv.get(i));

			System.out.println(iv.get(i).getImage());
		}

		chooseCar.setLayoutX(5); // select 3 cars
		chooseCar.setLayoutY(5);
		chooseCar.setId("chose");

		chooseSpeed.setLayoutX(5);// select speed
		chooseSpeed.setLayoutY(260);
		chooseSpeed.setId("choseS");

		chooseCase.setLayoutX(400);// select scenarios
		chooseCase.setLayoutY(5);
		chooseCase.setId("choseC");

		SL1.setToggleGroup(group); // speed buttons
		SL1.setUserData(40);
		SL2.setToggleGroup(group);
		SL2.setUserData(50);
		SL3.setToggleGroup(group);
		SL3.setUserData(60);

		SL1.setLayoutX(50);
		SL1.setLayoutY(300);

		SL2.setLayoutX(50);
		SL2.setLayoutY(330);

		SL3.setLayoutX(50);
		SL3.setLayoutY(360);

		SN1.setToggleGroup(group2);
		SN2.setToggleGroup(group2);

		SN1.setLayoutX(400);// scenario buttons
		SN1.setLayoutY(50);

		SN2.setLayoutX(400);
		SN2.setLayoutY(80);

		Go.setLayoutX(400);
		Go.setLayoutY(100);
		
		
		
		ivA.setImage(soneImage);
		ivA.setScaleX(.3);
		ivA.setScaleY(.3);
		ivA.setLayoutX(400);
		ivA.setLayoutY(-100);
		ivA.setVisible(false);
		
		
		// Rectangle2D viewportRect = new Rectangle2D(600, 50, 20, 30);
      //   ivA.setViewport(viewportRect);
		
	
		

		listA.add(carBoxck);
		listA.add(chooseCar);
		listA.add(chooseSpeed);
		listA.add(chooseCase);
		listA.add(SL1);
		listA.add(SL2);
		listA.add(SL3);
		listA.add(SN2);
		listA.add(SN1);
		listA.add(Go);
		
		listA.add(ivA);
		

		/*
		 * login page
		 */

		list.add(welcome);
		list.add(scenetitle);
		list.add(userName);
		list.add(userTextField);

		list.add(pw);
		list.add(pwBox);
		list.add(login);
		list.add(author);

		myroota.getChildren().addAll(list);

		gc.setFill(Color.RED);

		myAdminStage.show();

		cb.get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(0).isSelected()) {
					iv.get(0).setVisible(true);
					iv.get(0).setLayoutX(60);
					iv.get(0).setLayoutY(10);

				} else {
					iv.get(0).setVisible(false);

				}
			}

		});

		cb.get(1).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(1).isSelected()) {
					iv.get(1).setVisible(true);
					iv.get(1).setLayoutX(120);
					iv.get(1).setLayoutY(10);

				} else {
					iv.get(1).setVisible(false);
				}

			}

		});

		cb.get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(2).isSelected()) {
					iv.get(2).setVisible(true);
					iv.get(2).setLayoutX(180);
					iv.get(2).setLayoutY(10);

				} else {
					iv.get(2).setVisible(false);
				}

			}

		});

		cb.get(3).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(3).isSelected()) {

					iv.get(3).setVisible(true);
					iv.get(3).setLayoutX(60);
					iv.get(3).setLayoutY(40);
				} else {
					iv.get(3).setVisible(false);
				}

			}

		});

		cb.get(4).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(4).isSelected()) {

					iv.get(4).setVisible(true);
					iv.get(4).setLayoutX(120);
					iv.get(4).setLayoutY(40);
				} else {
					iv.get(4).setVisible(false);
				}

			}

		});

		cb.get(5).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(5).isSelected()) {

					iv.get(5).setVisible(true);
					iv.get(5).setLayoutX(180);
					iv.get(5).setLayoutY(40);
				} else {
					iv.get(5).setVisible(false);
				}

			}

		});

		cb.get(6).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(6).isSelected()) {

					iv.get(6).setVisible(true);
					iv.get(6).setLayoutX(60);
					iv.get(6).setLayoutY(70);
				} else {
					iv.get(6).setVisible(false);
				}

			}

		});

		cb.get(7).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(7).isSelected()) {

					iv.get(7).setVisible(true);
					iv.get(7).setLayoutX(120);
					iv.get(7).setLayoutY(70);
				} else {
					iv.get(7).setVisible(false);
				}

			}

		});

		cb.get(8).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(8).isSelected()) {

					iv.get(8).setVisible(true);
					iv.get(8).setLayoutX(180);
					iv.get(8).setLayoutY(70);
				} else {
					iv.get(8).setVisible(false);
				}

			}

		});

		cb.get(9).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(9).isSelected()) {

					iv.get(9).setVisible(true);
					iv.get(9).setLayoutX(60);
					iv.get(9).setLayoutY(100);

				} else {
					iv.get(9).setVisible(false);
				}
			}

		});

		cb.get(10).setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println("ok:");

				if (cb.get(10).isSelected()) {

					iv.get(10).setVisible(true);
					iv.get(10).setLayoutX(120);
					iv.get(10).setLayoutY(100);

				} else {
					iv.get(10).setVisible(false);
				}

			}

		});

		login.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (uname.equals(userTextField.getText())
						&& passw.equals(pwBox.getText())) {

					System.out.println("good");

					Iterator<Node> namesIterator = list.iterator();

					// Traversing elements
					while (namesIterator.hasNext()) {
						namesIterator.next().setVisible(false);
					}

					myroota.getChildren().addAll(listA);

				} else {
					System.out.println("bad");

				}

			}
		});

		Go.setOnMouseClicked(new EventHandler<MouseEvent>() {

			int ck, counter;

			public void handle(MouseEvent event) {

				for (ck = 0; ck < iv.size(); ck++) {
					if (cb.get(ck).isSelected()) {
						counter++;
						System.out.println(cb.get(ck).getText());
						carSelection.add(cb.get(ck).getText());
					}
				}
				
				speed=group.getSelectedToggle().getUserData().toString();
				
				//System.out.println(speed);
				
				
				if (counter > 3) {

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Please select 3 cars.");
					alert.setHeaderText("The game only plays with 3 cars");
					alert.setContentText("Select any 3 cars!");
					alert.showAndWait();

					counter = 0;

				} else {
					try {
						runEngine tsa= new runEngine(carSelection, speed);
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
		
		
		SN1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if(SN1.isSelected()){
					
					ivA.setVisible(true);
				}
				
				
				
			}
		});


		SN2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if(SN2.isSelected()){
					
					ivA.setVisible(false);
				}
				
				
				
			}
		});

		
	}

	

}
