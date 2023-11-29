package model.firefighterelements.entities;

import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.FireFighter.FireExtinguisher;
import util.Position;
import util.RandomGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Cloud extends FireExtinguisher {

    public Cloud(Set<Position> firePositions, int initialCount,  int rowCount, int columnCount) {
        super(firePositions, rowCount, columnCount, initialCount);
        this.tag = FFModelElement.CLOUD;
        initializeElements();
    }

    public List<Position> update(FirefighterBoard board){

        List<Position> result = new ArrayList<>();
        if (firePositions.isEmpty()) return result;

        List<Position> cloudNewPosition = new ArrayList<>();
        for (Position cloudPosition : getPositions()) {
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

        positions = cloudNewPosition;
        return result;
    }

    public Position cloudRandomPosition(Position position) {
        int row = position.row();
        int column = position.column();
        int random = RandomGenerator.randomInt(0, 3);
        switch (random) {
            case 0 -> {
                if (row > 0) return new Position(row - 1, column);
                else return new Position(row + 1, column);
            }
            case 1 -> {
                if (column > 0) return new Position(row, column - 1);
                else return new Position(row, column + 1);
            }
            case 2 -> {
                if (row < rowCount - 1) return new Position(row + 1, column);
                else return new Position(row - 1, column);
            }
            case 3 -> {
                if (column < columnCount - 1) return new Position(row, column + 1);
                else return new Position(row, column - 1);
            }
        }
        return position;
    }

    @Override
    public Collection<Position> getPositions() {
        return null;
    }

    @Override
    public void initializeElements() {

    }
}
