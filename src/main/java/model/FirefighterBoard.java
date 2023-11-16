package model;

import model.elements.*;
import util.Position;

import java.util.*;

public class FirefighterBoard implements Board<List<ModelElement>> {
  private final int columnCount;
  private final int rowCount;
  private final int initialFireCount;
  private final int initialFireFighterPerson;
  private final int initialFireTruckCount;

  private final int initialCloudCount;
  private List<Position> firefighterPositions;
  private Set<Position> firePositions;
  private int step = 0;
  private FireFighter firefighter;
  private FireTruck fireTruck;
  private Fire fire;
  private Cloud cloud;
  private Road road;
  private Mountain mountain;
  private Rock rock;

  public FirefighterBoard(int columnCount, int rowCount, int initialFireCount, int initialFireFighterPerson, int initialCloudCount, int initialFireTruckCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;
    this.initialFireCount = initialFireCount;
    this.initialFireFighterPerson = initialFireFighterPerson;
    this.initialFireTruckCount = initialFireTruckCount;
    this.initialCloudCount = initialCloudCount;

    initializeElements();
  }

  public void initializeElements() {
    fire = new Fire(initialFireCount, step, rowCount, columnCount);
    firefighter = new FireFighterPerson(fire.getPositions(), initialFireFighterPerson, rowCount, columnCount);
    fireTruck = new FireTruck(fire.getPositions(), initialFireTruckCount, rowCount, columnCount);
    cloud = new Cloud(fire.getPositions(), initialCloudCount, rowCount, columnCount);
    firefighterPositions = firefighter.getPositions();
    firePositions = fire.getPositions();
    road = new Road(new ArrayList<>(), rowCount, columnCount);
    mountain = new Mountain(new ArrayList<>(), rowCount, columnCount);
    rock = new Rock(new ArrayList<>(), rowCount, columnCount);

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
    List<Position> result = fire.update(road, mountain, rock);
    result.addAll(fireTruck.update(firePositions, road, mountain));
    result.addAll(firefighter.update(firePositions, road, mountain));
    result.addAll(cloud.update(firePositions));
    step++;
    fire.updateStep(step);
    return result;
  }

  public List<BoardElement> getBoardElements(){
    List<BoardElement> elements = new ArrayList<>();
    elements.add(firefighter);
    elements.add(fireTruck);
    elements.add(fire);
    elements.add(cloud);
    elements.add(road);
    elements.add(mountain);
    elements.add(rock);
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
  public List<ModelElement> getState(Position position) {
    List<ModelElement> result = new ArrayList<>();
    for(BoardElement element : getBoardElements()) {
        result.addAll(element.getState(position));
    }
    return result;
  }

  @Override
  public void setState(List<ModelElement> state, Position position) {
    for(BoardElement element : getBoardElements()) {
        element.setState(state, position);
    }
  }

  public void printBoard(){
    for(int i = 0; i < rowCount; i++){
      for(int j = 0; j < columnCount; j++){
        Position position = new Position(i, j);
        List<ModelElement> state = getState(position);
        if(state.contains(ModelElement.FIRE)){
          System.out.print("[F]");
        } else if(state.contains(ModelElement.FIREFIGHTERPERSON)) {
          System.out.print("[P]");
        } else if(state.contains(ModelElement.CLOUD)) {
          System.out.print("[C]");
        } else if(state.contains(ModelElement.FIRETRUCK)){
            System.out.print("[T]");
        } else if(state.contains(ModelElement.MOUNTAIN)){
            System.out.print("[M]");
        } else if(state.contains(ModelElement.ROAD)){
            System.out.print("[R]");
        } else if (state.contains(ModelElement.ROCK)){
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

  public Set<Position> getFirePositions(){
    return firePositions;
  }

  public void clearBoard(){
    firePositions.clear();
    firefighterPositions.clear();
    step = 0;

    // Clear other elements
    fire.initializeElements(initialFireCount);
    firefighter.initializeElements(initialFireFighterPerson);
    fireTruck.initializeElements(initialFireTruckCount);
    cloud.initializeElements(initialCloudCount);
    road.initializeElements(0);
    mountain.initializeElements(0);
    rock.initializeElements(0);
  }
}
