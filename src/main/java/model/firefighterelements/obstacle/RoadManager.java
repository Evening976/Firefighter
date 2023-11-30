package model.firefighterelements.obstacle;

import general.model.entities.ModelElement;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class RoadManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> roads;
    public RoadManager(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        initialCount = (int) (rowCount*columnCount*0.2);
        roads = new HashSet<>();
        tag = new FFModelElement(Color.GRAY, "[R]");
        initializeElements();
    }

    public boolean isObstacle(Position position) {
        for (Obstacle road : roads){
            if (road.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    public void initializeElements() {
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
    public void setState(List<? extends ModelElement> state, Position position) {
        roads.removeIf(road -> road.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
                roads.add(new Road(position));
            }
        }
    }

    public List<Position> getPositions(){
        List<Position> positions = new ArrayList<>();
        for(Obstacle road: roads)
            positions.add(road.getPosition());
        return positions;
    }

    @Override
    public boolean contains(Position position) {
        for(Obstacle road: roads) {
            if (road.getPosition().equals(position)) return true;
        }
        return false;
    }
}
