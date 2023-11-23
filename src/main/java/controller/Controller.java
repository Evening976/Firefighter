package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import javafx.util.Pair;
import model.Board;
import model.PlagueBoard;
import model.firefighterelements.FFModelElement;
import model.FirefighterBoard;
import util.Position;
import view.Grid;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Controller {

  public static final int PERIOD_IN_MILLISECONDS = 50;
  @FXML
  public Button restartButton;
  @FXML
  public Button oneStepButton;
  @FXML
  public Label generationNumberLabel;
  @FXML
  private ToggleButton pauseToggleButton;
  @FXML
  private ToggleButton playToggleButton;
  @FXML
  private Grid<ViewElement> grid;
  private Timeline timeline;
  private Board<List<FFModelElement>> board;

  @FXML
  private void initialize() {
    initializePlayAndPauseToggleButtons();
    initializeTimeline();
  }

  private void initializePlayAndPauseToggleButtons() {
    ToggleGroup toggleGroup = new PersistentToggleGroup();
    toggleGroup.getToggles().addAll(playToggleButton, pauseToggleButton);
    pauseToggleButton.setSelected(true);
  }

  private void setModel(FirefighterBoard firefighterBoard) {
    this.board = requireNonNull(firefighterBoard, "firefighter.model is null");
  }
  private void setModel(PlagueBoard plagueBoard) {
    //this.board = requireNonNull(plagueBoard, "plague.model is null");
  }

  private void updateBoard(){
    List<Position> updatedPositions = board.updateToNextGeneration();
    List<Pair<Position, ViewElement>> updatedSquares = new ArrayList<>();
    for(Position updatedPosition : updatedPositions){
      List<FFModelElement> squareState = board.getState(updatedPosition);
      ViewElement viewElement = getViewElement(squareState);
      updatedSquares.add(new Pair<>(updatedPosition, viewElement));
    }
    grid.repaint(updatedSquares);
    updateGenerationLabel(board.stepNumber());
  }

  private void repaintGrid(){
    int columnCount = board.columnCount();
    int rowCount = board.rowCount();
    ViewElement[][] viewElements = new ViewElement[rowCount][columnCount];
    for(int column = 0; column < columnCount; column++)
      for(int row = 0; row < rowCount; row++)
        viewElements[row][column] = getViewElement(board.getState(new Position(row, column)));
    grid.repaint(viewElements);
    updateGenerationLabel(board.stepNumber());
  }

  private ViewElement getViewElement(List<FFModelElement> squareState) {

    if(squareState.contains(FFModelElement.FIREFIGHTERPERSON)){
      return ViewElement.FIREFIGHTERPERSON;
    }
    if(squareState.contains(FFModelElement.FIRETRUCK)){
      return ViewElement.FIRETRUCK;
    }
    if (squareState.contains(FFModelElement.FIRE)){
      return ViewElement.FIRE;
    }
    if(squareState.contains(FFModelElement.CLOUD)) {
      return ViewElement.CLOUD;
    }
      if(squareState.contains(FFModelElement.ROAD)) {
      return ViewElement.ROAD;
    }
    if(squareState.contains(FFModelElement.MOUNTAIN)) {
      return ViewElement.MOUNTAIN;
    }
    if(squareState.contains(FFModelElement.ROCK)) {
      return ViewElement.ROCK;
    }
    return ViewElement.EMPTY;
  }

  private void initializeTimeline() {
    Duration duration = new Duration(Controller.PERIOD_IN_MILLISECONDS);
    EventHandler<ActionEvent> eventHandler =
            event -> updateBoard();
    KeyFrame keyFrame = new KeyFrame(duration, eventHandler);
    timeline = new Timeline(keyFrame);
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  public void play() {
    timeline.play();
  }

  public void pause() {
    timeline.pause();
  }

  public void pauseToggleButtonAction() {
    this.pause();
  }

  public void playToggleButtonAction() {
    this.play();
  }

  public void restartButtonAction() {
    this.pause();
    board.reset();
    pauseToggleButton.setSelected(true);
    repaintGrid();
  }

  public void initialize(int squareWidth, int squareHeight, int columnCount,
                                int rowCount, int initialFireCount, int initialFirefighterCount, int initialCloudCount, int initialFireTruckCount) {
    grid.setDimensions(columnCount, rowCount, squareWidth, squareHeight);
    this.setModel(new FirefighterBoard(columnCount, rowCount, initialFireCount, initialFirefighterCount, initialCloudCount, initialFireTruckCount));
    repaintGrid();
  }

  public void oneStepButtonAction() {
    this.pause();
    updateBoard();
  }

  private void updateGenerationLabel(int value){
    generationNumberLabel.setText(Integer.toString(value));
  }
}