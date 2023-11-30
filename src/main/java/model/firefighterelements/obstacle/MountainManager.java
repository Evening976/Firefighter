package model.firefighterelements.obstacle;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class MountainManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> mountains;
    public MountainManager(int rowCount, int columnCount) {
        initialCount = (int) (rowCount * columnCount * 0.2);
        mountains = new HashSet<>();
        initializeElements(rowCount, columnCount);
    }

    public List<Position> getPositions(){
        List<Position> positions = new ArrayList<>();
        for(Obstacle mountain: mountains)
            positions.add(mountain.getPosition());
        return positions;
    }

    public void initializeElements(int rowCount, int columnCount) {
        for (int index = 0; index < initialCount; index++) {
            mountains.add(new Mountain(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return mountains;
    }

    @Override
    public boolean accept(Position position) {
        return !contains(position);
    }

    @Override
    public ModelElement getState(Position position) {
        if(contains(position)) {return FFModelElement.MOUNTAIN;}
        return ModelElement.EMPTY;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        mountains.removeIf(mountain -> mountain.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(FFModelElement.MOUNTAIN)){
                mountains.add(new Mountain(position));
            }
        }
    }

    @Override
    public boolean contains(Position position) {
        for(Obstacle mountain: mountains) {
            if (mountain.getPosition().equals(position))
                return true;
        }
        return false;
    }
}
