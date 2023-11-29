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
        initialCount = (int) (rowCount * columnCount * 0.1);
        mountains = new HashSet<>();
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
        for(Obstacle mountain: mountains) {
            if (mountain.getPosition().equals(position))
                return true;
        }
        return false;
    }
}
