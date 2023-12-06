package model.firefighter.entities;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;
import util.RandomGenerator;

import java.util.*;

public class CloudManager extends EntityManager {
    Set<Cloud> clouds;

    public CloudManager(int initialCount, int rowCount, int columnCount) {
        super(rowCount, columnCount, initialCount);
        this.tag = new FFModelElement(Color.CYAN, "[C]");
        clouds = new HashSet<>();
        initializeElements();
    }

    public List<Position> update(FireManager fireManager){
        List<Position> result = new ArrayList<>();
        if(fireManager.getPositions().isEmpty()) return result;

        Set<Cloud> cloudNewPosition = new HashSet<>();
        for (Position cloudPosition : getPositions()) {
            Position newCloudPosition = cloudRandomPosition(cloudPosition);
            cloudNewPosition.add(new Cloud(newCloudPosition));
            fireManager.extinguish(newCloudPosition);
            result.add(cloudPosition);
            result.add(newCloudPosition);
            List<Position> neighborFirePositions = neighbors(newCloudPosition);
            for (Position firePosition : neighborFirePositions) {
                fireManager.extinguish(firePosition);
            }
            result.addAll(neighborFirePositions);
        }
        clouds.clear();
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
    public List<Position> getPositions() {
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


    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        clouds.removeIf(cloud -> cloud.getPosition().equals(position));
        for(ModelElement element: state) {
            if (element.equals(tag)) {
                clouds.add(new Cloud(position));
            }
        }
    }
}
