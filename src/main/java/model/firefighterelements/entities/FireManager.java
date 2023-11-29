package model.firefighterelements.entities;

import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import general.model.entity.EntityManager;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.obstacle.Mountain;
import util.Position;

import java.util.*;

public class FireManager extends EntityManager {
    Set<Fire> fires;
    List<ObstacleManager> obstacleManagers;
    public FireManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);
        tag = FFModelElement.FIRE;
        this.obstacleManagers = Arrays.asList(obstacleManagers);
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            List<Position> nextPositions = new ArrayList<>();
            Position randomPosition = Position.randomPosition(rowCount, columnCount);
            Position sidePosition = new Position(randomPosition.row() - 1, randomPosition.column());
            for (int current = 1; current < 10; current++) {
                nextPositions.add(new Position (randomPosition.row(), randomPosition.column() - current));
                nextPositions.add(new Position(sidePosition.row(), sidePosition.column() - current));
            }
            for (Position position : nextPositions) {
                if (!getPositions().contains(position)) fires.add(new Fire(position));
            }
        }
    }

    public List<Position> update(FirefighterBoard board) {
        int step = board.getStep();
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            Set<Fire> newFirePositions = new HashSet<>();
            for (Position fire : getPositions()) {
                for (Position nextPosition : neighbors(fire)) {
                    for (ObstacleManager obstacleManager : obstacleManagers) {
                        if (obstacleManager.accept(nextPosition)) {
                            newFirePositions.add(new Fire(nextPosition));
                            result.add(nextPosition);
                        }
                    }
                }
            }
            fires.addAll(newFirePositions);
        }
        return result;
    }

    @Override
    public Collection<Position> getPositions() {
        Collection<Position> positions = new ArrayList<>();
        for(Fire fire: fires)
            positions.add(fire.getPosition());
        return positions;
    }
}


