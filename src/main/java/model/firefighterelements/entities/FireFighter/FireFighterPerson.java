package model.firefighterelements.entities.FireFighter;

import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.*;

public class FireFighterPerson extends FireFighter {
    public FireFighterPerson(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
        tag = FFModelElement.FIREFIGHTERPERSON;
    }
}
