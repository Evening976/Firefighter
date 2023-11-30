package model.firefighterelements.obstacle;

import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import general.model.obstacle.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class RockManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> rocks;

    public RockManager(int rowCount, int columnCount) {
        initialCount = (int) (rowCount * 0.5);
        rocks = new HashSet<>();
        tag = new FFModelElement(Color.PURPLE, "[X]");
        initializeElements(rowCount, columnCount);
    }

    @Override
    public void initializeElements(int rowCount, int columnCount) {
        for (int index = 0; index < initialCount; index++) {
            rocks.add(new Rock(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return rocks;
    }

    @Override
    public boolean accept(Position position) {
        for (Obstacle rock : rocks)
            if (rock.getPosition().equals(position)) return rock.isObstacle(position);
        return true;
    }

    @Override
    public ModelElement getState(Position position) {
        if(contains(position)) return tag;
        return ModelElement.EMPTY;
    }

    public List<Position> getPositions(){
        List<Position> positions = new ArrayList<>();
        for(Obstacle rock: rocks)
            positions.add(rock.getPosition());
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        rocks.removeIf(rock -> rock.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
                rocks.add(new Rock(position));
            }
        }
    }
}
