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
    List<Position> result = fire.update();
    result.addAll(fireTruck.update(firePositions));
    result.addAll(firefighter.update(firePositions));
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
        } else {
          System.out.print("[ ]");
        }
      }
      System.out.println();
    }
  }

  public Set<Position> getFirePositions(){
    return firePositions;
  }
}
