package model;

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
  private final Random randomGenerator = new Random();
  private final FirefighterUpdater firefighterUpdater;
  private final FireUpdater fireUpdater;

  public FirefighterBoard(int columnCount, int rowCount, int initialFireCount, int initialFirefighterCount, int initialCloudCount) {
    this.columnCount = columnCount;
    this.rowCount = rowCount;
    this.initialFireCount = initialFireCount;
    this.initialFirefighterCount = initialFirefighterCount;
    initializeElements();
    firefighterUpdater = new FirefighterUpdater(firefighterPositions, firePositions, rowCount, columnCount);
    fireUpdater = new FireUpdater(firePositions, step, rowCount, columnCount);
  }

  public void initializeElements() {
    firefighterPositions = new ArrayList<>();
    firePositions = new HashSet<>();
    for (int index = 0; index < initialFireCount; index++)
      firePositions.add(randomPosition());
    for (int index = 0; index < initialFirefighterCount; index++)
      firefighterPositions.add(randomPosition());
  }

  private Position randomPosition() {
    return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
  }

  @Override
  public List<ModelElement> getState(Position position) {
    List<ModelElement> result = new ArrayList<>();
    for (Position firefighterPosition : firefighterPositions)
      if (firefighterPosition.equals(position))
        result.add(ModelElement.FIREFIGHTER);
    if (firePositions.contains(position))
      result.add(ModelElement.FIRE);
    return result;
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
    List<Position> result = new ArrayList<>();
    firefighterUpdater.updatePositions(firePositions);
    result.addAll(firefighterUpdater.update());
    result.addAll(fireUpdater.update());
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
  public void setState(List<ModelElement> state, Position position) {
    firePositions.remove(position);
    for (;;) {
      if (!firefighterPositions.remove(position)) break;
    }
    for (ModelElement element : state) {
      switch (element) {
        case FIRE -> firePositions.add(position);
        case FIREFIGHTER -> firefighterPositions.add(position);
      }
    }
  }
}
