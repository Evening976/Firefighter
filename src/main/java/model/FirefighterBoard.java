package model;

import general.model.GameElement;
import general.model.entities.ModelElement;
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
  public static final int INITIAL_FIRE_COUNT = 3;
  public static final int INITIAL_FIREFIGHTER_COUNT = 3;
  public static final int INITIAL_CLOUD_COUNT = 3;
  public static final int INITIAL_FIRETRUCK_COUNT = 2;
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

    fireManager = new FireManager(INITIAL_FIRE_COUNT, rowCount, columnCount, mountainManager, rockManager, roadManager);
    fireFighterManager = new FireFighterPersonManager(INITIAL_FIREFIGHTER_COUNT, rowCount, columnCount, mountainManager);
    fireTruckManager = new FireTruckManager(INITIAL_FIRETRUCK_COUNT, rowCount, columnCount, mountainManager);
    cloudManager = new CloudManager(INITIAL_CLOUD_COUNT, rowCount, columnCount);
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
    List<Position> result = fireManager.update(stepNumber());
    result.addAll(fireTruckManager.update(fireManager));
    result.addAll(fireFighterManager.update(fireManager));
    result.addAll(cloudManager.update(fireManager));

    if(!fireManager.getPositions().isEmpty()) {
      step++;
    }

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
    List<GameElement> gameElements = new ArrayList<>();
    gameElements.add(mountainManager);
    gameElements.add(rockManager);
    gameElements.add(roadManager);
    gameElements.add(fireManager);
    gameElements.add(fireFighterManager);
    gameElements.add(fireTruckManager);
    gameElements.add(cloudManager);
    return gameElements;
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
        System.out.print(getState(new Position(i, j)).getValue().tag());
      }
      System.out.println();
    }
    System.out.println("___________________");
  }
}
