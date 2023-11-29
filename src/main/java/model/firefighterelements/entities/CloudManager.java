package model.firefighterelements.entities;

import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.FireFighter.FireExtinguisher;
import util.Position;
import util.RandomGenerator;

import java.util.*;

public class CloudManager extends FireExtinguisher {
    Set<Cloud> clouds;

    public CloudManager(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, rowCount, columnCount, initialCount);
        this.tag = FFModelElement.CLOUD;
        clouds = new HashSet<>();
        initializeElements();
    }

    public List<Position> update(FirefighterBoard board){

        List<Position> result = new ArrayList<>();
        if (firePositions.isEmpty()) return result;

        Set<Cloud> cloudNewPosition = new HashSet<>();
        for (Position cloudPosition : getPositions()) {
            Position newCloudPosition = cloudRandomPosition(cloudPosition);
            cloudNewPosition.add(new Cloud(newCloudPosition));
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
        clouds = cloudNewPosition;
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
        List<Position> positions = new ArrayList<>();
        for (Cloud cloud : clouds) {
            positions.add(cloud.getPosition());
        }
        return positions;
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            clouds.add(new Cloud(Position.randomPosition(rowCount, columnCount)));
        }
    }
}
