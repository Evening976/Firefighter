package model;

import app.SimulatorApplication;
import general.model.entity.ModelElement;
import model.firefighterelements.Entity;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.FireFighter;
import model.firefighterelements.entities.Cloud;
import model.firefighterelements.entities.Fire;
import model.firefighterelements.entities.FireFighterPerson;
import model.firefighterelements.entities.FireTruck;
import model.firefighterelements.obstacle.Mountain;
import model.firefighterelements.obstacle.Road;
import model.firefighterelements.obstacle.Rock;
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
  private Fire fire;
  private Cloud cloud;
  private Road road;
  private Mountain mountain;
  private Rock rock;

  public FirefighterBoard(int columnCount, int rowCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;

    initializeElements();
  }

  public void initializeElements() {
    fire = new Fire(SimulatorApplication.INITIAL_FIRE_COUNT, rowCount, columnCount);
    firefighter = new FireFighterPerson((Set<Position>) fire.getPositions(), SimulatorApplication.INITIAL_FIREFIGHTER_COUNT, rowCount, columnCount);
    fireTruck = new FireTruck((Set<Position>) fire.getPositions(), SimulatorApplication.INITIAL_FIRETRUCK_COUNT, rowCount, columnCount);
    cloud = new Cloud((Set<Position>) fire.getPositions(), SimulatorApplication.INITIAL_CLOUD_COUNT, rowCount, columnCount);
    road = new Road(rowCount, columnCount);
    mountain = new Mountain(rowCount, columnCount);
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
    elements.add(fireTruck);
    elements.add(fire);
    elements.add(cloud);
    elements.add(road);
    elements.add(mountain);
    elements.add(rock);
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
    for(Entity element : getBoardElements()) {
      ModelElement e = element.getState(position);
      if(e instanceof FFModelElement) result.add((FFModelElement) element.getState(position));
      else result.add(FFModelElement.EMPTY);
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
    return fire.getPositions().contains(position);
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
    return (Set<Position>) fire.getPositions();
  }

  public void clearBoard(){
    step = 0;
    fire.initializeElements();
    firefighter.initializeElements();
    fireTruck.initializeElements();
    cloud.initializeElements();
    road.initializeElements(0);
    mountain.initializeElements(0);
    rock.initializeElements(0);
  }
}
