package model.firefighterelements.entities;

import general.model.entity.ModelElement;
import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import general.model.entity.EntityManager;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.obstacle.Mountain;
import util.Position;

import java.util.*;

public class FireManager extends EntityManager {
    Set<Fire> fires;
    List<ObstacleManager> obstacleManagers;
    public FireManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);
        fires = new HashSet<>();
        tag = FFModelElement.FIRE;
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

    public List<Position> update(FirefighterBoard board) {
        List<Position> result = new ArrayList<>();
        if (board.stepNumber() % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : getPositions()) {
                for (Position nextPosition : neighbors(fire)) {
                    if(fires.contains(new Fire(nextPosition))) break;
                    if(!obstacleManagers.stream().allMatch(obstacleManager -> obstacleManager.accept(nextPosition))) continue;
                        newFirePositions.add(nextPosition);
                }
            }
            fires.clear();
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
                System.out.println("Fire extinguished at " + position);
            }
        }
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


