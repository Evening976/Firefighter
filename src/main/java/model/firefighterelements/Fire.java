package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public class Fire extends FFBoardElement {
    private Set<Position> firePositions;

    public Fire(Set<Position> firePositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.firePositions = firePositions;
    }

    public Fire(int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount);
        initializeElements(initialCount);
    }


    public List<Position> update(FirefighterBoard board) {
        int step = board.getStep();
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : firePositions) {
                List<Position> fireNeighbors = board.neighbors(fire);
                for (Position neighbor : fireNeighbors) {
                    if (board.fireCanSpread(neighbor)) {
                        if (board.isRock(neighbor)) {
                            if (!firePositions.contains(neighbor) && step % 4 == 0 && step != 0) {
                                newFirePositions.add(neighbor);
                            }
                        } else {
                            newFirePositions.add(neighbor);
                        }
                    }
                }
            }
            firePositions.addAll(newFirePositions);
            result.addAll(newFirePositions);
        }
        return result;
    }


    @Override
    public Set<Position> getPositions() {
        return firePositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        firePositions = new HashSet<>();
        for (int index = 0; index < initialCount; index++) {
            firePositions.add(randomPosition(rowCount, columnCount));
        }
    }

    @Override
    public List<FFModelElement> getState(Position position) {
        List<FFModelElement> result = new ArrayList<>();
        if (firePositions.contains(position))
            result.add(FFModelElement.FIRE);
        return result;
    }

    @Override
    public void setState(List<FFModelElement> state, Position position) {
        firePositions.remove(position);
        for(FFModelElement element : state) {
            if (element.equals(FFModelElement.FIRE)) {
                firePositions.add(position);
            }
        }
    }
}


