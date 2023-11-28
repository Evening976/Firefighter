package model;

import model.firefighterelements.*;
import util.Position;

import java.util.*;

public class FirefighterBoard implements Board<List<FFModelElement>> {
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
    fire = new Fire(initialFireCount, rowCount, columnCount);
    firefighter = new FireFighterPerson((Set<Position>) fire.getPositions(), initialFireFighterPerson, rowCount, columnCount);
    fireTruck = new FireTruck((Set<Position>) fire.getPositions(), initialFireTruckCount, rowCount, columnCount);
    cloud = new Cloud((Set<Position>) fire.getPositions(), initialCloudCount, rowCount, columnCount);
    firefighterPositions = (List<Position>) firefighter.getPositions();
    firePositions = (Set<Position>) fire.getPositions();
    road = new Road(rowCount, columnCount);
    mountain = new Mountain(new ArrayList<>(), rowCount, columnCount);
    rock = new Rock(rowCount, columnCount);

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
    List<Position> result = fire.update(this);

    result.addAll(fireTruck.update(this));
    result.addAll(firefighter.update(this));
    result.addAll(cloud.update(this));

    step++;

    return result;
  }

  public int getStep(){
    return step;
  }


  public List<Entity> getBoardElements(){
    List<Entity> elements = new ArrayList<>();
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
  public List<FFModelElement> getState(Position position) {
    List<FFModelElement> result = new ArrayList<>();
    for(Entity element : getBoardElements()) {
        result.add(element.getState(position));
    }
    return result;
  }

  @Override
  public void setState(List<FFModelElement> state, Position position) {
    for(Entity element : getBoardElements()) {
        element.setState(state, position);
    }
  }

  public boolean fireCanSpread(Position position){
    return (mountain.fireCanSpread(position) && road.fireCanSpread(position));
  }

  public boolean fireFighterCanMove(Position position){
    return (mountain.isCrossable(position));
  }

  public boolean isFire(Position position){
    return firePositions.contains(position);
  }

  public boolean isRock(Position position){
    return rock.isRock(position);
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

  public List<Position> neighbors(Position position){
    List<Position> list = new ArrayList<>();
    if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
    if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
    if (position.row() < rowCount - 1) list.add(new Position(position.row() + 1, position.column()));
    if (position.column() < columnCount - 1) list.add(new Position(position.row(), position.column() + 1));
    return list;
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
