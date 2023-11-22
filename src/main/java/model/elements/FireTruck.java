package model.elements;

import util.Position;

import java.time.Month;
import java.util.*;

public class FireTruck extends FireFighter {

    public FireTruck(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, Set<Position> firePositions, Road road,  Mountain mountain) {
        Position step1 = steps(position, firePositions, road, mountain);
        return steps(step1, firePositions, road, mountain);
    }

    private Position steps(Position position, Set<Position> firePositions, Road road, Mountain mountain) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));
        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (firePositions.contains(current) && !mountain.isMountain(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors(current)) {
                if (seen.contains(adjacent) || mountain.isMountain(current)) {
                    continue;
                }
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        List<Position> firefighterPositions = getPositions();
        for (Position firefighterPosition : firefighterPositions) {
            if (firefighterPosition.equals(position)){
                result.add(ModelElement.FIRETRUCK);
            }
        }
        return result;
    }


    @Override
    public void setState(List<ModelElement> state, Position position) {
        List<Position> firefighterPositions = getPositions();
        for (;;) {
            if (!firefighterPositions.remove(position)) break;
        }
        for (ModelElement element : state) {
            if (element.equals(ModelElement.FIRETRUCK)) {
                firefighterPositions.add(position);
            }
        }
    }
}
