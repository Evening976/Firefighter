package model;

import app.SimulatorApplication;
import general.model.GameElement;
import general.model.entity.ModelElement;
import javafx.util.Pair;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.CloudManager;
import model.firefighterelements.entities.FireFighter.FireFighter;
import model.firefighterelements.entities.FireFighter.FireFighterPersonManager;
import model.firefighterelements.entities.FireFighter.FireTruckManager;
import model.firefighterelements.entities.FireManager;
import model.firefighterelements.obstacle.MountainManager;
import model.firefighterelements.obstacle.RoadManager;
import model.firefighterelements.obstacle.RockManager;
import util.Position;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

public class FirefighterBoard implements Board<List<FFModelElement>> {
  private final int columnCount;
  private final int rowCount;
  private int step = 0;
  private FireFighter fireFighterManager;
  private FireTruckManager fireTruckManager;
  public FireManager fireManager;
  private CloudManager cloudManager;
  private MountainManager mountainManager;
  private RockManager rockManager;
  private RoadManager roadManager;

  public FirefighterBoard(int columnCount, int rowCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;

    initializeElements();
  }

  public void initializeElements() {
    roadManager = new RoadManager(rowCount, columnCount);
    mountainManager = new MountainManager(rowCount, columnCount);
    rockManager = new RockManager(rowCount, columnCount);

    fireManager = new FireManager(SimulatorApplication.INITIAL_FIRE_COUNT, rowCount, columnCount, mountainManager, rockManager, roadManager);
    fireFighterManager = new FireFighterPersonManager(fireManager.getPositions(), SimulatorApplication.INITIAL_FIREFIGHTER_COUNT, rowCount, columnCount, mountainManager);
    fireTruckManager = new FireTruckManager(fireManager.getPositions(), SimulatorApplication.INITIAL_FIRETRUCK_COUNT, rowCount, columnCount, mountainManager);
    cloudManager = new CloudManager(fireManager.getPositions(), SimulatorApplication.INITIAL_CLOUD_COUNT, rowCount, columnCount);
  }


  @Override
  public int rowCount() {
    return rowCount;
  }

  @Override
  public int columnCount() {
    return columnCount;
  }

  public List<Position> updateToNextGeneration() {
    List<Position> result = fireManager.update(this);
    result.addAll(fireTruckManager.update(this));
    result.addAll(fireFighterManager.update(this));
    result.addAll(cloudManager.update(this));

    step++;

    return result;
  }

  @Override
  public int stepNumber() {
    return step;
  }

  @Override
  public void reset() {
    step = 0;
    initializeElements();
  }

  @Override
  public Pair<Position, ViewElement> getState(Position position) {
    Pair<Position, ViewElement> result = new Pair<>(position, new ViewElement());

    for(GameElement element: getGameElements()){
        ModelElement e = element.getState(position);
        if(e instanceof FFModelElement) result = (new Pair<>(position, new ViewElement(e.getValue(), e.getTag())));
    }

    return result;
  }

  List<GameElement> getGameElements(){
    List<GameElement> result = new ArrayList<>();
    result.add(mountainManager);
    result.add(rockManager);
    result.add(roadManager);
    result.add(fireManager);
    result.add(fireFighterManager);
    result.add(fireTruckManager);
    result.add(cloudManager);
    return result;
  }

  @Override
  public void setState(List<FFModelElement> state, Position position) {
    for(GameElement element: getGameElements()){
        element.setState(state, position);
    }
  }

  public void printBoard(){
    for(int i = 0; i < rowCount; i++){
      for(int j = 0; j < columnCount; j++){
        Position position = new Position(i, j);
        String cell = "[ ]";
        if(fireManager.getState(position)
        List<FFModelElement> state = getState(position).getValue();
        if(state.contains(FFModelElement.FIRE)){
          System.out.print("[F]");
        } else if(state.contains(FFModelElement.FIREFIGHTERPERSON)) {
          System.out.print("[P]");
        } else if(state.contains(FFModelElement.CLOUD)) {
          System.out.print("[C]");
        } else if(state.contains(FFModelElement.FIRETRUCK)){
            System.out.print("[T]");
        } else if(state.contains(FFModelElement.MOUNTAIN)){
            System.out.print("[M]");
        } else if(state.contains(FFModelElement.ROAD)){
            System.out.print("[R]");
        } else if (state.contains(FFModelElement.ROCK)){
            System.out.print("[X]");
        }
        else {
          System.out.print("[ ]");
        }
      }
      System.out.println();
    }
    System.out.println("___________________");
  }

  public void clearBoard(){
    step = 0;

    fireManager.initializeElements();
    fireFighterManager.initializeElements();
    fireTruckManager.initializeElements();
    cloudManager.initializeElements();
    roadManager.initializeElements(rowCount, columnCount);
    mountainManager.initializeElements(rowCount, columnCount);
    rockManager.initializeElements(rowCount, columnCount);
  }
}
