package model.firefighterelements.obstacle;

import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class RockManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> rocks;

    public RockManager(int rowCount, int columnCount) {
        initialCount = (int) (rowCount * 0.5);

        initializeElements(rowCount, columnCount);
    }

    @Override
    public void initializeElements(int rowCount, int columnCount) {
        for (int index = 0; index < initialCount; index++) {
            List<Position> nextPositions = new ArrayList<>();
            Position randomPosition = Position.randomPosition(rowCount, columnCount);
            Position sidePosition = new Position(randomPosition.row() - 1, randomPosition.column());
            for (int current = 1; current < 10; current++) {
                nextPositions.add(new Position(randomPosition.row(), randomPosition.column() - current));
                nextPositions.add(new Position(sidePosition.row(), sidePosition.column() - current));
            }
            for (Position position : nextPositions) {
                if (!contains(position)) rocks.add(new Rock(position));
            }
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return rocks;
    }

    @Override
    public ModelElement getState(Position position) {
        if(contains(position)) return FFModelElement.ROCK;
        return ModelElement.EMPTY;
    }

    public Collection<Position> getPositions(){
        Collection<Position> positions = new ArrayList<>();
        for(Obstacle rock: rocks)
            positions.add(rock.getPosition());
        return positions;
    }

    @Override
    public void setState(Collection<? extends ModelElement> state, Position position) {
        List<Position> entityPositions = (List<Position>) getPositions();
        for(;;){
            if(!entityPositions.remove(position)) break;
        }
        for(ModelElement element: state){
            if(element.equals(FFModelElement.ROCK)){
                entityPositions.add(position);
            }
        }
    }

    public void updateStep(int step) {
        for(Obstacle rock: rocks) {
            ((Rock)rock).setStep(step);
        }
    }
}
