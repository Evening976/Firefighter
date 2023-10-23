package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import javafx.util.Pair;
import model.Board;
import model.ModelElement;
import model.FirefighterBoard;
import model.Position;
import view.FirefighterGrid;
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
  private ToggleButton pauseToggleButton;
  @FXML
  private ToggleButton playToggleButton;
  @FXML
  private FirefighterGrid grid;
  private Timeline timeline;
  private Board<List<ModelElement>> board;

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
    this.board = requireNonNull(firefighterBoard, "model is null");
  }

  private void updateBoard(){
    List<Position> updatedPositions = board.updateToNextGeneration();
    List<Pair<Position, ViewElement>> updatedSquares = new ArrayList<>();
    for(Position updatedPosition : updatedPositions){
      List<ModelElement> squareState = board.getState(updatedPosition);
      ViewElement viewElement = getViewElement(squareState);
      updatedSquares.add(new Pair<>(updatedPosition, viewElement));
    }
    grid.repaint(updatedSquares);
  }

  private void repaintBoard(){
    int columnCount = board.columnCount();
    int rowCount = board.rowCount();
    ViewElement[][] viewElements = new ViewElement[rowCount][columnCount];
    for(int column = 0; column < columnCount; column++)
      for(int row = 0; row < rowCount; row++)
        viewElements[row][column] = getViewElement(board.getState(new Position(row, column)));
    grid.repaint(viewElements);
  }

  private ViewElement getViewElement(List<ModelElement> squareState) {
    if(squareState.contains(ModelElement.FIREFIGHTER)){
      return ViewElement.FIREFIGHTER;
    }
    if (squareState.contains(ModelElement.FIRE)){
      return ViewElement.FIRE;
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
    repaintBoard();
  }

  public void initialize(int squareWidth, int squareHeight, int columnCount,
                                int rowCount, int initialFireCount, int initialFirefighterCount) {
    grid.initialize(squareWidth, squareHeight, columnCount, rowCount);
    this.setModel(new FirefighterBoard(columnCount, rowCount, initialFireCount, initialFirefighterCount));
    repaintBoard();
  }

  public void oneStepButtonAction() {
    this.pause();
    updateBoard();
  }
}