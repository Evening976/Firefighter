package model.firefighterelements.entities.FireFighter;

import general.model.entity.ModelElement;
import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.Cloud;
import util.Position;

import java.util.*;
import java.util.stream.Collectors;

public class FireFighterPersonManager extends FireFighter {
    Set<FireFighterPerson> fireFighterPerson;
    public FireFighterPersonManager(Set<Position> firePositions, int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers) {
        super(firePositions, initialCount, rowCount, columnCount, obstacleManagers);
        fireFighterPerson = new HashSet<>();
        tag = FFModelElement.FIREFIGHTERPERSON;
        initializeElements();
    }

    @Override
    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (FireFighterPerson fireFighterPerson : fireFighterPerson) {
            positions.add(fireFighterPerson.getPosition());
        }
        return positions;
    }

    @Override
    public List<Position> update(FirefighterBoard board) {
        System.out.println("local  " + firePositions);
        System.out.println("global " + board.fireManager.getPositions());

        List<Position> result = new ArrayList<>();
        Set<FireFighterPerson> firefighterNewPositions = new HashSet<>();

        for (Position firefighterPosition : getPositions()) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, board);
            firefighterNewPositions.add(new FireFighterPerson(newFirefighterPosition));
            //board.fireManager.extinguish(newFirefighterPosition);
            extinguish(newFirefighterPosition);
            result.add(firefighterPosition);
            result.add(newFirefighterPosition);
            List<Position> neighborFirePositions = neighbors(newFirefighterPosition).stream()
                    .filter(firePositions::contains)
                    .toList();
            for (Position firePosition : neighborFirePositions) {
                extinguish(firePosition);
                //board.fireManager.extinguish(firePosition);
            }
            result.addAll(neighborFirePositions);
        }
        fireFighterPerson.clear();
        fireFighterPerson.addAll(firefighterNewPositions);

        return result;
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            fireFighterPerson.add(new FireFighterPerson(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        fireFighterPerson.removeIf(fireFighterPerson -> fireFighterPerson.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(FFModelElement.FIREFIGHTERPERSON)){
                fireFighterPerson.add(new FireFighterPerson(position));
            }
        }
    }
}
