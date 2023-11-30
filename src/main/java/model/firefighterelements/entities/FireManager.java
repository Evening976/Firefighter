package model.firefighterelements.entities;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import general.model.obstacle.ObstacleManager;
import javafx.scene.paint.Color;
import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class FireManager extends EntityManager {
    Set<Fire> fires;
    List<ObstacleManager> obstacleManagers;
    public FireManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);
        fires = new HashSet<>();
        tag = new FFModelElement(Color.RED, "[F]");
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
    public ModelElement getState(Position position) {
        if(getPositions().contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
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

    public List<Position> update(FirefighterBoard board) {
        List<Position> result = new ArrayList<>();
        if (board.stepNumber() % 2 == 0) {
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


