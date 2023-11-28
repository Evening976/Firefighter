package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public class Fire extends Entity {

    public Fire(int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount);

        tag = FFModelElement.FIRE;
        positions = new HashSet<>();

        initializeElements(initialCount);
    }

    public List<Position> update(FirefighterBoard board) {
        int step = board.getStep();
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : getPositions()) {
                for (Position neighbor : board.neighbors(fire)) {
                    if (board.fireCanSpread(neighbor)) {
                        if (board.isRock(neighbor)) {
                            if (!positions.contains(neighbor) && step % 4 == 0 && step != 0) {
                                newFirePositions.add(neighbor);
                            }
                        } else {
                            newFirePositions.add(neighbor);
                        }
                    }
                }
            }
            positions.addAll(newFirePositions);
            result.addAll(newFirePositions);
        }
        return result;
    }
}


