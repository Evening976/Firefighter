package model.rockPaperScissors;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import util.Position;
import model.RPSBoard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScissorsManager extends EntityManager {
    Set<Scissors> scissors;

    public ScissorsManager(int initialCount, int rowCount, int columnCount) {
        super(rowCount, columnCount, initialCount);
        scissors = new HashSet<>();
        tag = RPSModelElement.SCISSORS;
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            scissors.add(new Scissors(Position.randomPosition(rowCount, columnCount)));
        }
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        List<Position> scissorsPosition = new ArrayList<>();
        for (Position scissor : getPositions()) {
            for (Position nextPosition : neighbors(scissor)) {
                if (scissors.contains(new Scissors(nextPosition))) continue;

                if (board.scissorsCanConquer(nextPosition)) {
                    scissorsPosition.add(nextPosition);
                }
            }
        }
        for (Position position : scissorsPosition) {
            scissors.add(new Scissors(position));
        }
        result.addAll(scissorsPosition);
        return result;
    }

    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for (Scissors scissor : scissors) {
            positions.add(scissor.getPosition());
        }
        return positions;
    }

    @Override
    public ModelElement getState(Position position) {
        if (getPositions().contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        scissors.removeIf(scissor -> scissor.getPosition().equals(position));
        for (ModelElement modelElement : state) {
            if (modelElement.equals(tag)) {
                scissors.add(new Scissors(position));
            }
        }
    }
}