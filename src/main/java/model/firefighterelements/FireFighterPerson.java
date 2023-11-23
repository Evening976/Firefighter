package model.firefighterelements;

import util.Position;

import java.util.*;

public class FireFighterPerson extends FireFighter{
    public FireFighterPerson(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, Set<Position> firePositions, Road road, Mountain mountain) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (firePositions.contains(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors(current)) {
                if (seen.contains(adjacent) || mountain.isMountain(current)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
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
