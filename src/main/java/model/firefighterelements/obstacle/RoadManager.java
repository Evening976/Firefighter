package model.firefighterelements.obstacle;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class RoadManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> roads;
    public RoadManager(int rowCount, int columnCount) {
        initialCount = (int) (rowCount*columnCount*0.1);
        roads = new HashSet<>();
        initializeElements(rowCount, columnCount);
    }

    public boolean isObstacle(Position position){
        for(Obstacle road: roads)
            if(road.getPosition().equals(position))
                return true;
        return false;
    }

    @Override
    public void initializeElements(int rowCount, int columnCount) {
        for (int index = 0; index < initialCount; index++) {
            roads.add(new Road(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return roads;
    }

    @Override
    public boolean accept(Position position) {
        return !contains(position);
    }

    @Override
    public ModelElement getState(Position position) {
        if(isObstacle(position)) return FFModelElement.ROAD;
        return ModelElement.EMPTY;
    }

    public Collection<Position> getPositions(){
        Collection<Position> positions = new ArrayList<>();
        for(Obstacle road: roads)
            positions.add(road.getPosition());
        return positions;
    }

    @Override
    public void setState(Collection<? extends ModelElement> state, Position position) {
        List<Position> entityPositions = (List<Position>) getPositions();
        for(;;){
            if(!entityPositions.remove(position)) break;
        }
        for(ModelElement element: state){
            if(element.equals(FFModelElement.ROAD)){
                entityPositions.add(position);
            }
        }
    }

    @Override
    public boolean contains(Position position) {
        for(Obstacle road: roads) {
            if (road.getPosition().equals(position)) return true;
        }
        return false;
    }
}
