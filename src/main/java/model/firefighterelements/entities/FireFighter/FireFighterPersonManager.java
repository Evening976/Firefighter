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

public class FireFighterPersonManager extends FireFighter {
    Set<FireFighterPerson> fireFighterPerson;
    public FireFighterPersonManager(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers) {
        super(initialCount, rowCount, columnCount, obstacleManagers);
        fireFighterPerson = new HashSet<>();
        tag = new FFModelElement(Color.BLUE, "[P]");
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
    public List<Position> update(FireManager fireManager) {
        List<Position> result = new ArrayList<>();
        Set<FireFighterPerson> firefighterNewPositions = new HashSet<>();
        for (Position firefighterPosition : getPositions()) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, fireManager);
            firefighterNewPositions.add(new FireFighterPerson(newFirefighterPosition));
            fireManager.extinguish(newFirefighterPosition);
            result.add(firefighterPosition);
            result.add(newFirefighterPosition);
            List<Position> neighborFirePositions = neighbors(newFirefighterPosition);
            for (Position firePosition : neighborFirePositions) {
                fireManager.extinguish(firePosition);
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
    public ModelElement getState(Position position) {
        if(getPositions().contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        fireFighterPerson.removeIf(fireFighterPerson -> fireFighterPerson.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
                fireFighterPerson.add(new FireFighterPerson(position));
            }
        }
    }
}
