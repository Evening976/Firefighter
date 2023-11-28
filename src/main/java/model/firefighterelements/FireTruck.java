package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

public class FireTruck extends FireFighter {

    public FireTruck(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
        tag = FFModelElement.FIRETRUCK;
    }
    @Override
    public Position neighborClosestToFire(Position position, FirefighterBoard board) {
        Position step1 = steps(position, board);
        return steps(step1, board);
    }

    private Position steps(Position position, FirefighterBoard board) {
        return getPosition(position, board);
    }

}
