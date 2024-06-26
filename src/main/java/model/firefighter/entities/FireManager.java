package model.firefighter.entities;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class FireManager extends EntityManager {
    Set<Fire> fires;
    List<ObstacleManager> obstacleManagers;
    public FireManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);
        this.tag = new FFModelElement(Color.RED, "[F]");
        fires = new HashSet<>();
        this.obstacleManagers = Arrays.asList(obstacleManagers);
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            fires.add(new Fire(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        fires.removeIf(fire -> fire.getPosition().equals(position));
        for(ModelElement modelElement: state){
            if(modelElement.equals(tag)){
                fires.add(new Fire(position));
            }
        }
    }

    public List<Position> update(int step) {
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : getPositions()) {
                for (Position nextPosition : neighbors(fire)) {
                    if(fires.contains(new Fire(nextPosition))) continue;
                    if(!obstacleManagers.stream().allMatch(obstacleManager -> obstacleManager.accept(nextPosition))) continue;
                    newFirePositions.add(nextPosition);
                }
            }
            for (Position position : newFirePositions) {
                fires.add(new Fire(position));
            }
            result.addAll(newFirePositions);
        }
        return result;
    }

    public void extinguish(Position position){
        Set<Fire> firesNewPositions = new HashSet<>(fires);
        for(Fire fire: fires){
            if(fire.getPosition().equals(position)){
                firesNewPositions.remove(fire);
            }
        }
        fires.clear();
        fires = firesNewPositions;
    }

    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for(Fire fire: fires) {
            positions.add(fire.getPosition());
        }
        return positions;
    }
}


