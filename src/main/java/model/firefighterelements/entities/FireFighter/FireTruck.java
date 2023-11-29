package model.firefighterelements.entities.FireFighter;

import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.entities.FireFighter.FireFighter;
import util.Position;

import java.util.*;

public class FireTruck extends FireFighter {

    public FireTruck(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
        tag = FFModelElement.FIRETRUCK;
    }
    @Override
    public Position neighborClosestToFire(Position position, FirefighterBoard board) {
        return super.neighborClosestToFire(super.neighborClosestToFire(position, board), board);
    }
}
