package model.firefighterelements.entities.FireFighter;

import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.Cloud;
import model.firefighterelements.entities.Fire;
import util.Position;

import java.util.*;
import java.util.stream.Collectors;

public class FireTruckManager extends FireFighter {
    Set<FireTruck> fireTrucks;

    public FireTruckManager(Set<Position> firePositions, int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers) {
        super(firePositions, initialCount, rowCount, columnCount, obstacleManagers);
        fireTrucks = new HashSet<>();
        tag = FFModelElement.FIRETRUCK;
        initializeElements();
    }
    @Override
    public Position neighborClosestToFire(Position position, FirefighterBoard board) {
        return super.neighborClosestToFire(super.neighborClosestToFire(position, board), board);
    }
    @Override
    public List<Position> update(FirefighterBoard board) {
        List<Position> result = new ArrayList<>();
        List<Position> firefighterNewPositions = new ArrayList<>();
        for (Position firefighterPosition : getPositions()) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, board);
            firefighterNewPositions.add(newFirefighterPosition);
            board.fireManager.extinguish(newFirefighterPosition);
            //extinguish(newFirefighterPosition);
            result.add(firefighterPosition);
            result.add(newFirefighterPosition);
            List<Position> neighborFirePositions = neighbors(newFirefighterPosition).stream()
                    .filter(board::isFire)
                    .toList();
            for (Position firePosition : neighborFirePositions) {
                board.fireManager.extinguish(firePosition);
                //extinguish(firePosition);
            }
            result.addAll(neighborFirePositions);
        }
        fireTrucks.clear();
        for(Position position: firefighterNewPositions){
            fireTrucks.add(new FireTruck(position));
        }
        return result;
    }

    @Override
    public Collection<Position> getPositions() {
        Collection<Position> positions = new ArrayList<>();
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
}
