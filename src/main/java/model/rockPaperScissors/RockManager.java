package model.rockPaperScissors;
import model.RPSBoard;


import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import util.Position;

import java.util.*;


public class RockManager extends EntityManager {
    Set<Rock> rocks;

    public RockManager(int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount, initialCount);
        rocks = new HashSet<>();
        tag = RPSModelElement.ROCK;
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            rocks.add(new Rock(Position.randomPosition(rowCount, columnCount)));
        }
    }


    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        List<Position> rocksPosition = new ArrayList<>();
        for (Position rock : getPositions()) {
            for (Position nextPosition : neighbors(rock)) {
                if (rocks.contains(new Rock(nextPosition))) continue;

                if (board.rockCanConquer(nextPosition)) {
                    rocksPosition.add(nextPosition);
                }
            }
        }
        for (Position position : rocksPosition) {
            rocks.add(new Rock(position));
        }
        result.addAll(rocksPosition);
        return result;
    }

    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for(Rock rock : rocks) {
            positions.add(rock.getPosition());
        }
        return positions;
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
        rocks.removeIf(rock -> rock.getPosition().equals(position));
        for(ModelElement modelElement: state){
            if(modelElement.equals(tag)){
                rocks.add(new Rock(position));
            }
        }
    }


}
