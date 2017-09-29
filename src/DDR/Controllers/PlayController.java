package DDR.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayController {

    @FXML
    private Label scoreDisplay;

    @FXML
    private Polygon downStart;

    @FXML
    private Polygon leftStart;

    @FXML
    private AnchorPane background;

    @FXML
    private Polygon rightStart;

    @FXML
    private Polygon upStart;

    @FXML
    private Polygon downGoal;

    @FXML
    private Polygon rightGoal;

    @FXML
    private Polygon leftGoal;

    @FXML
    private Polygon upGoal;

    private Thread updateTimer;

    private Thread addTimer;

    private Runnable adder;

    private Runnable doer;

    private List<Polygon> arrows = new ArrayList<>();

    private double speed;

    private int score = 0;

    private int mutex = 0;

    public PlayController(){
        Runnable init = this::init;
        updateTimer = new Thread(this::timer);
        addTimer = new Thread(this::add);
        adder = this::addArrow;
        doer = this::update;

        Platform.runLater(init);

        speed = 1;

        updateTimer.setName("timer");
        updateTimer.start();
        addTimer.start();
    }

    private void init(){
        leftStart.setLayoutX(leftGoal.getLayoutX());
        downStart.setLayoutX(downGoal.getLayoutX());
        upStart.setLayoutX(upGoal.getLayoutX());
        rightStart.setLayoutX(rightGoal.getLayoutX());
        scoreDisplay.setText("Score: " + Integer.toString(score));
        makeRight();
        background.requestFocus();
    }

    private void timer(){
        try {
            while (true) {
                Platform.runLater(doer);
                Thread.sleep(1000/60);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupt caught\n");
        }
    }

    private void update(){
        while(mutex == 1){

        }
        mutex = 1;
        remove();
        move();
        mutex = 0;
    }


    void add(){
        while (true) {
            try {
                Thread.sleep((long) (500 / speed));
                Platform.runLater(adder);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    void addArrow(){

        while(mutex == 1){

        }
        mutex = 1;

        Random x = new Random();
        int y = x.nextInt(4) + 1;
        switch (y){
            case 1:
                makeLeft();
                break;
            case 2:
                makeDown();
                break;
            case 3:
                makeUp();
                break;
            case 4:
                makeRight();
                break;
        }

        mutex = 0;
    }

    //checkers

    void check(double layoutX){
        for (int i = 0; i < arrows.size(); i++) {
            if(arrows.get(i).getLayoutY() < rightGoal.getLayoutY() + (50 * speed) && arrows.get(i).getLayoutX() == layoutX){
                score++;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                background.getChildren().remove(arrows.get(i));
                arrows.remove(i);
            }
        }
    }

    void remove(){
        for (int i = 0; i < arrows.size(); i++) {
            if(arrows.get(i).getLayoutY() < -50){
                background.getChildren().remove(arrows.get(i));
                arrows.remove(i);
            }

        }
    }

    //makers

    void makeLeft(){
        Polygon newArrow = new Polygon(-50,40,50,40,0,-60);
        newArrow.setRotate(leftStart.getRotate());
        newArrow.setLayoutX(leftStart.getLayoutX());
        newArrow.setLayoutY(leftStart.getLayoutY());
        newArrow.setOpacity(1);
        newArrow.setFill(Color.RED);
        background.getChildren().add(newArrow);
        arrows.add(newArrow);
    }

    void makeUp(){
        Polygon newArrow = new Polygon(-50,40,50,40,0,-60);
        newArrow.setRotate(upStart.getRotate());
        newArrow.setLayoutX(upStart.getLayoutX());
        newArrow.setLayoutY(upStart.getLayoutY());
        newArrow.setOpacity(1);
        newArrow.setFill(Color.RED);
        background.getChildren().add(newArrow);
        arrows.add(newArrow);
    }

    void makeDown(){
        Polygon newArrow = new Polygon(-50,40,50,40,0,-60);
        newArrow.setRotate(downStart.getRotate());
        newArrow.setLayoutX(downStart.getLayoutX());
        newArrow.setLayoutY(downStart.getLayoutY());
        newArrow.setOpacity(1);
        newArrow.setFill(Color.RED);
        background.getChildren().add(newArrow);
        arrows.add(newArrow);
    }

    void makeRight(){
        Polygon newArrow = new Polygon(-50,40,50,40,0,-60);
        newArrow.setRotate(rightStart.getRotate());
        newArrow.setLayoutX(rightStart.getLayoutX());
        newArrow.setLayoutY(rightStart.getLayoutY());
        newArrow.setFill(Color.RED);
        background.getChildren().add(newArrow);
        arrows.add(newArrow);
    }

    //movers

    private void move(){
        int movement = (int) (speed * (background.getHeight() / 60));
        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).setLayoutY(arrows.get(i).getLayoutY() - movement);
        }
    }

    @FXML
    void keyHandler(KeyEvent event) {

        if(event.getCode().equals(KeyCode.LEFT)){
            check(leftGoal.getLayoutX());
        } else if (event.getCode().equals(KeyCode.DOWN)){
            check(downGoal.getLayoutX());

        } else if (event.getCode().equals(KeyCode.UP)){
            check(upGoal.getLayoutX());

        } else if (event.getCode().equals(KeyCode.RIGHT)){
            check(rightGoal.getLayoutX());
        }
        background.requestFocus();
    }

}

