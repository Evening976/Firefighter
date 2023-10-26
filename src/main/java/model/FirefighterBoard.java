package model;

import model.elements.BoardElement;
import model.elements.Fire;
import model.elements.FireFighter;
import model.elements.ModelElement;
import util.Position;

import java.util.*;

public class FirefighterBoard implements Board<List<ModelElement>> {
  private final int columnCount;
  private final int rowCount;
  private final int initialFireCount;
  private final int initialFirefighterCount;
  private List<Position> firefighterPositions;
  private Set<Position> firePositions;
  private int step = 0;
  private FireFighter firefighter;
  private Fire fire;

  public FirefighterBoard(int columnCount, int rowCount, int initialFireCount, int initialFirefighterCount, int initialCloudCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;
    this.initialFireCount = initialFireCount;
    this.initialFirefighterCount = initialFirefighterCount;
    initializeElements();
  }

  public void initializeElements() {
    fire = new Fire(initialFireCount, step, rowCount, columnCount);
    firefighter = new FireFighter(fire.getPositions(), initialFirefighterCount, rowCount, columnCount);
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
    result.addAll(firefighter.update(firePositions));
    step++;
    return result;
  }

  public List<BoardElement> getBoardElements(){
    List<BoardElement> elements = new ArrayList<>();
    elements.add(firefighter);
    elements.add(fire);
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
}
