package model.firefighterelements.obstacle;

import general.model.entities.ModelElement;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class MountainManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> mountains;
    public MountainManager(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        initialCount = (int) (rowCount * columnCount * 0.2);
        mountains = new HashSet<>();
        tag = new FFModelElement(Color.BLACK,"[M]");
        initializeElements();
    }

    public List<Position> getPositions(){
        List<Position> positions = new ArrayList<>();
        for(Obstacle mountain: mountains)
            positions.add(mountain.getPosition());
        return positions;
    }

    public void initializeElements() {
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
    public void setState(List<? extends ModelElement> state, Position position) {
        mountains.removeIf(mountain -> mountain.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
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
