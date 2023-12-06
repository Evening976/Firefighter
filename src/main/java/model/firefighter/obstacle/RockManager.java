package model.firefighter.obstacle;

import general.model.entities.ModelElement;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class RockManager extends ObstacleManager {
    int initialCount;
    Set<Obstacle> rocks;

    public RockManager(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        initialCount = (int) (rowCount * 0.5);
        rocks = new HashSet<>();
        tag = new FFModelElement(Color.PURPLE, "[X]");
        initializeElements();
    }

    @Override
    public void initializeElements() {
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
