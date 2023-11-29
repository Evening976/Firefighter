package model.firefighterelements.entities;

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

    public List<Position> update(FirefighterBoard board) {
        List<Position> result = new ArrayList<>();
        if (board.getStep() % 2 == 0) {
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
        for(Fire fire: fires){
            if(fire.getPosition().equals(position)){
                fires.remove(fire);
                System.out.println("Fire extinguished at " + position);
                break;
            }
        }
    }

    @Override
    public Collection<Position> getPositions() {
        Collection<Position> positions = new HashSet<>();
        for(Fire fire: fires)
            positions.add(fire.getPosition());
        return positions;
    }
}


