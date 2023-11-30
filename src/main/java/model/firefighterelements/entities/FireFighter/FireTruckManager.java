package model.firefighterelements.entities.FireFighter;

import general.model.entity.ModelElement;
import general.model.obstacle.ObstacleManager;
import javafx.scene.paint.Color;
import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.FireManager;
import util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FireTruckManager extends FireFighter {
    Set<FireTruck> fireTrucks;

    public FireTruckManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers) {
        super(initialCount, rowCount, columnCount, obstacleManagers);
        fireTrucks = new HashSet<>();
        tag = new FFModelElement(Color.LIGHTSALMON, "[C]");
        initializeElements();
    }
    @Override
    public Position neighborClosestToFire(Position position, FireManager fireManager) {
        return super.neighborClosestToFire(super.neighborClosestToFire(position, fireManager), fireManager);
    }
    @Override
    public List<Position> update(FireManager fireManager) {
        List<Position> result = new ArrayList<>();
        Set<FireTruck> firefighterNewPositions = new HashSet<>();
        for (Position firefighterPosition : getPositions()) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, fireManager);
            firefighterNewPositions.add(new FireTruck(newFirefighterPosition));
            fireManager.extinguish(newFirefighterPosition);
            result.add(firefighterPosition);
            result.add(newFirefighterPosition);
            List<Position> neighborFirePositions = neighbors(newFirefighterPosition);
            for (Position firePosition : neighborFirePositions){
               fireManager.extinguish(firePosition);
            }
            result.addAll(neighborFirePositions);
        }
        fireTrucks.clear();
        fireTrucks.addAll(firefighterNewPositions);

        return result;
    }

    @Override
    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (FireTruck fireTruck : fireTrucks) {
            positions.add(fireTruck.getPosition());
        }
        return positions;
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            fireTrucks.add(new FireTruck(Position.randomPosition(rowCount, columnCount)));
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
        fireTrucks.removeIf(fireTruck -> fireTruck.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
                fireTrucks.add(new FireTruck(position));
            }
        }
    }
}
