package model.boardElements;

import model.elements.BoardElement;
import model.elements.ModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.List;

public class Rock extends BoardElement {
    private final List<Position> rockPositions;
    private final int fireResistanceSteps;
    private int stepsRemaining;

    public Rock(List<Position> rockPositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.rockPositions = rockPositions;
        this.fireResistanceSteps = 4; // Adjust this value as needed
        initializeElements(getInitCount());
    }

    public int getInitCount() {
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.05);
    }

    @Override
    public List<Position> getPositions() {
        return rockPositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        rockPositions.clear();
        for (int index = 0; index < initialCount; index++)
            rockPositions.add(new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount)));
        resetStepsRemaining();
    }

    @Override
    public List<model.elements.ModelElement> getState(Position position) {
        List<model.elements.ModelElement> result = new ArrayList<>();
        List<Position> rockPositions = getPositions();
        for (Position rockPosition : rockPositions) {
            if (rockPosition.equals(position)) {
                result.add(model.elements.ModelElement.ROCK);
            }
        }
        return result;
    }

    @Override
    public void setState(List<model.elements.ModelElement> state, Position position) {
        List<Position> rockPositions = getPositions();
        for (; ; ) {
            if (!rockPositions.remove(position)) break;
        }
        for (model.elements.ModelElement element : state) {
            if (element.equals(ModelElement.ROCK)) {
                rockPositions.add(position);
            }
        }
        resetStepsRemaining();
    }

    public boolean isRock(Position position) {
        return rockPositions.contains(position);
    }

    public boolean isFireResistant() {
        return stepsRemaining > 0;
    }

    public void decrementStepsRemaining() {
        stepsRemaining = Math.max(0, stepsRemaining - 1);
    }

    public void resetStepsRemaining() {
        stepsRemaining = fireResistanceSteps;
    }
}
