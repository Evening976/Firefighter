package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;
import util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Cloud extends Entity {

    private final Set<Position> firePositions;

    public Cloud(Set<Position> firePositions, int initialCount,  int rowCount, int columnCount) {
        super(rowCount, columnCount);

        this.firePositions = firePositions;
        positions = new ArrayList<>();
        tag = FFModelElement.CLOUD;

        initializeElements(initialCount);
    }

    private void extinguish(Position position) {
        firePositions.remove(position);
    }

    public List<Position> update(FirefighterBoard board){
        if(firePositions.isEmpty()) return new ArrayList<>();
        List<Position> result = new ArrayList<>();
        if (firePositions.isEmpty()) return result;
        List<Position> cloudNewPosition = new ArrayList<>();
        for (Position cloudPosition : getPositions()) {
            Position newCloudPosition = cloudRandomPosition(cloudPosition);
            cloudNewPosition.add(newCloudPosition);
            extinguish(newCloudPosition);
            result.add(cloudPosition);
            result.add(newCloudPosition);
            List<Position> neighborFirePositions = board.neighbors(newCloudPosition).stream()
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

}
