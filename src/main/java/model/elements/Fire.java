package model.elements;

import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public class Fire extends BoardElement {
    private Set<Position> firePositions;
    private int step;

    public Fire(Set<Position> firePositions, int step, int rowCount, int columnCount) {
        this.firePositions = firePositions;
        this.step = step;
        this.rowCount = rowCount;
        this.columnCount = columnCount;

    }

    public Fire(int initialCount, int step, int rowCount, int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.step = step;
        initializeElements(initialCount);
    }

    public void updateStep(int step){
        this.step = step;
    }


    public List<Position> update(Road road, Mountain mountain) {
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : firePositions) {
                List<Position> fireNeighbors = neighbors(fire);
                for (Position neighbor : fireNeighbors) {
                    if (road.fireCanSpread(neighbor) || mountain.fireCanSpread(neighbor)) {
                        newFirePositions.add(neighbor);
                    }
                }
            }
            firePositions.addAll(newFirePositions);
            result.addAll(newFirePositions);
        }
        return result;
    }


    @Override
    public Set<Position> getPositions() {
        return firePositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        firePositions = new HashSet<>();
        for (int index = 0; index < initialCount; index++) {
            firePositions.add(randomPosition(rowCount, columnCount));
        }
    }

    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        if (firePositions.contains(position))
            result.add(ModelElement.FIRE);
        return result;
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        firePositions.remove(position);
        for(ModelElement element : state) {
            if (element.equals(ModelElement.FIRE)) {
                firePositions.add(position);
            }
        }
    }
}


