package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

public class FireFighterPerson extends FireFighter{
    public FireFighterPerson(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, FirefighterBoard board) {
        return getPosition(position, board);
    }


    @Override
    public List<FFModelElement> getState(Position position) {
        List<FFModelElement> result = new ArrayList<>();
        List<Position> firefighterPositions = getPositions();
        for (Position firefighterPosition : firefighterPositions) {
            if (firefighterPosition.equals(position)){
                result.add(FFModelElement.FIREFIGHTERPERSON);
            }
        }
        return result;
    }

    @Override
    public void setState(List<FFModelElement> state, Position position) {
        List<Position> firefighterPositions = getPositions();
        for (;;) {
            if (!firefighterPositions.remove(position)) break;
        }
        for (FFModelElement element : state) {
            if (element.equals(FFModelElement.FIREFIGHTERPERSON)) {
                firefighterPositions.add(position);
            }
        }
    }
}
