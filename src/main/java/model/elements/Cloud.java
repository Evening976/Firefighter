package model.elements;

import javafx.geometry.Pos;
import util.Position;
import util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static util.RandomGenerator.randomPosition;

public class Cloud extends BoardElement{
    private List<Position> cloudPositions;
    private final Set<Position> firePositions;

    public Cloud(Set<Position> firePositions, int initialCount,  int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.firePositions = firePositions;
        initializeElements(initialCount);
    }

    private void extinguish(Position position) {
        firePositions.remove(position);
    }

    public List<Position> update(Set<Position> firePositions){
        List<Position> result = new ArrayList<>();
        if (firePositions.isEmpty()) return result;
        List<Position> cloudNewPosition = new ArrayList<>();
        for (Position cloudPosition : cloudPositions) {
            Position newCloudPosition = cloudRandomPosition(cloudPosition);
            cloudNewPosition.add(newCloudPosition);
            extinguish(newCloudPosition);
            result.add(cloudPosition);
            result.add(newCloudPosition);
            List<Position> neighborFirePositions = neighbors(newCloudPosition).stream()
                    .filter(firePositions::contains)
                    .toList();
            for (Position firePosition : neighborFirePositions)
                extinguish(firePosition);
            result.addAll(neighborFirePositions);
        }
        cloudPositions = cloudNewPosition;
        return result;
    }

    @Override
    public void initializeElements(int initialCount) {
        cloudPositions = new ArrayList<>();
        for(int index = 0; index < initialCount; index++)
            cloudPositions.add(randomPosition(rowCount, columnCount));
    }

    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        for (Position cloud : cloudPositions) {
            if (cloud.equals(position)){
                result.add(ModelElement.CLOUD);
            }
        }
        return result;
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        for (;;) {
            if (!cloudPositions.remove(position)) break;
        }
        for (ModelElement element : state) {
            if (element.equals(ModelElement.CLOUD)) {
                cloudPositions.add(position);
            }
        }
    }

    @Override
    public List<Position> getPositions() {
        return cloudPositions;
    }

    public Position cloudRandomPosition(Position position) {
        int row = position.row();
        int column = position.column();
        int random = RandomGenerator.randomInt(0, 3);
        switch (random) {
            case 0:
                if (row > 0) return new Position(row - 1, column);
                else return new Position(row + 1, column);
            case 1:
                if (column > 0) return new Position(row, column - 1);
                else return new Position(row, column + 1);
            case 2:
                if (row < rowCount - 1) return new Position(row + 1, column);
                else return new Position(row - 1, column);
            case 3:
                if (column < columnCount - 1) return new Position(row, column + 1);
                else return new Position(row, column - 1);
        }
        return position;
    }

}
