package model.firefighterelements.obstacle;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MountainManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> mountains;
    public MountainManager(int rowCount, int columnCount) {
        initialCount = (int) (rowCount * columnCount * 0.2);
        initializeElements(rowCount, columnCount);
    }

    public Collection<Position> getPositions(){
        Collection<Position> positions = new ArrayList<>();
        for(Obstacle mountain: mountains)
            positions.add(mountain.getPosition());
        return positions;
    }

    public void initializeElements(int rowCount, int columnCount) {
        for (int index = 0; index < initialCount; index++) {
            List<Position> nextPositions = new ArrayList<>();
            Position randomPosition = Position.randomPosition(rowCount, columnCount);
            Position sidePosition = new Position(randomPosition.row() - 1, randomPosition.column());
            for (int current = 1; current < 10; current++) {
                nextPositions.add(new Position (randomPosition.row(), randomPosition.column() - current));
                nextPositions.add(new Position(sidePosition.row(), sidePosition.column() - current));
            }
            for (Position position : nextPositions) {
                if (!contains(position)) mountains.add(new Mountain(position));
            }
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return mountains;
    }

    @Override
    public boolean accept(Position position) {
        for(Obstacle mountain: mountains)
            if(mountain.isObstacle(position))
                return false;
        return true;
    }


    @Override
    public ModelElement getState(Position position) {
        if(contains(position)) return FFModelElement.MOUNTAIN;
        return ModelElement.EMPTY;
    }

    @Override
    public void setState(Collection<? extends ModelElement> state, Position position) {
        List<Position> entityPositions = (List<Position>) getPositions();
        for(;;){
            if(!entityPositions.remove(position)) break;
        }
        for(ModelElement element: state){
            if(element.equals(FFModelElement.MOUNTAIN)){
                entityPositions.add(position);
            }
        }
    }

    @Override
    public boolean contains(Position position) {
        for(Obstacle mountain: mountains)
            if(mountain.getPosition().equals(position))
                return true;
        return false;
    }
    public boolean isCrossable(Position position){
        return !contains(position);
    }
}
