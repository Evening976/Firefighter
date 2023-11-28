package model.firefighterelements.entities;

import model.FirefighterBoard;
import model.firefighterelements.FFModelElement;
import model.firefighterelements.FireFighter;
import util.Position;

import java.util.*;

public class FireFighterPerson extends FireFighter {
    public FireFighterPerson(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
        tag = FFModelElement.FIREFIGHTERPERSON;
    }

    @Override
    public Position neighborClosestToFire(Position position, FirefighterBoard board) {
        return getPosition(position, board);
    }
}