package model;

import app.SimulatorApplication;
import general.model.entity.ModelElement;
import general.model.entity.EntityManager;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.FireFighter.FireFighter;
import model.firefighterelements.entities.Cloud;
import model.firefighterelements.entities.FireManager;
import model.firefighterelements.entities.FireFighter.FireFighterPerson;
import model.firefighterelements.entities.FireFighter.FireTruck;
import model.firefighterelements.obstacle.MountainManager;
import model.firefighterelements.obstacle.RoadManager;
import model.firefighterelements.obstacle.RockManager;
import util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirefighterBoard implements Board<List<FFModelElement>> {
  private final int columnCount;
  private final int rowCount;
  private int step = 0;
  private FireFighter firefighter;
  private FireTruck fireTruck;
  private FireManager fireManager;
  private Cloud cloud;
  List<ObstacleManager> ob;

  public FirefighterBoard(int columnCount, int rowCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;

    initializeElements();
  }

  public void initializeElements() {
    fireManager = new FireManager(SimulatorApplication.INITIAL_FIRE_COUNT, rowCount, columnCount);
    firefighter = new FireFighterPerson((Set<Position>) fireManager.getPositions(), SimulatorApplication.INITIAL_FIREFIGHTER_COUNT, rowCount, columnCount);
    fireTruck = new FireTruck((Set<Position>) fireManager.getPositions(), SimulatorApplication.INITIAL_FIRETRUCK_COUNT, rowCount, columnCount);
    cloud = new Cloud((Set<Position>) fireManager.getPositions(), SimulatorApplication.INITIAL_CLOUD_COUNT, rowCount, columnCount);
    ob.add(new RoadManager(rowCount, columnCount));
    ob.add(new MountainManager(rowCount, columnCount));
    ob.add(new RockManager(rowCount, columnCount));
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

    result.addAll(fireTruck.update(this));
    result.addAll(firefighter.update(this));
    result.addAll(cloud.update(this));

    for(ObstacleManager obstacle : ob){
      if(obstacle instanceof RockManager){
        ((RockManager) obstacle).updateStep(step);
      }
    }

    step++;

    return result;
  }

  public int getStep(){
    return step;
  }


  public List<EntityManager> getBoardElements(){
    List<EntityManager> elements = new ArrayList<>();
    elements.add(fireTruck);
    elements.add(fireManager);
    elements.add(cloud);
    elements.add(firefighter);
    return elements;
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
  public List<FFModelElement> getState(Position position) {
    List<FFModelElement> result = new ArrayList<>();

    for(EntityManager element : getBoardElements()) {
      ModelElement e = element.getState(position);
      if(e instanceof FFModelElement) result.add((FFModelElement) element.getState(position));
      else result.add(FFModelElement.EMPTY);
    }

    for(ObstacleManager om : ob){
        ModelElement e = om.getState(position);
        if(e instanceof FFModelElement) result.add((FFModelElement) om.getState(position));
        else result.add(FFModelElement.EMPTY);
    }

    return result;
  }

  @Override
  public void setState(List<FFModelElement> state, Position position) {
    for(EntityManager element : getBoardElements()) {
        element.setState(state, position);
    }
    for(ObstacleManager obstacle : ob){
        obstacle.setState(state, position);
    }
  }

  public boolean fireCanSpread(Position position){
    for(ObstacleManager obstacle : ob){
      for(Obstacle o : obstacle.getObstacles()){
        if(o.isObstacle(position)) return false;
      }
    }
    return true;
  }

  public boolean fireFighterCanMove(Position position){
    for(ObstacleManager obstacle : ob){
      if(obstacle instanceof MountainManager){
        return ((MountainManager) obstacle).isCrossable(position);
      }
    }
    return true;
  }

  public boolean isFire(Position position){
    return fireManager.getPositions().contains(position);
  }

  public void printBoard(){
    for(int i = 0; i < rowCount; i++){
      for(int j = 0; j < columnCount; j++){
        Position position = new Position(i, j);
        List<FFModelElement> state = getState(position);
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
    firefighter.initializeElements();
    fireTruck.initializeElements();
    cloud.initializeElements();

    for(ObstacleManager obstacle : ob){
      obstacle.initializeElements(rowCount, columnCount);
    }
  }
}
