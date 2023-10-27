package model.elements;

import util.Position;

import java.util.*;

public class FireTruck extends FireFighter{

    public FireTruck(Set<Position> firePositions, int initialCount, int rowCount, int columnCount){
        super( firePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, Set<Position> firePositions) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));
        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);

        int stepsRemaining = 2;

        while (!toVisit.isEmpty()) {
            int firstToVisist = toVisit.size();

            if (stepsRemaining == 0) {
                return firstMove.get(toVisit.poll());
            }

            for (int i = 0; i < firstToVisist; i++) {
                Position current = toVisit.poll();

                if (firePositions.contains(current)) {
                    stepsRemaining = 0;
                    return current;
                }

                assert current != null;
                for (Position adjacent : neighbors(current)) {
                    if (seen.contains(adjacent)) continue;
                    toVisit.add(adjacent);
                    seen.add(adjacent);
                    firstMove.put(adjacent, firstMove.get(current));
                }
            }
            stepsRemaining--;
        }

        return firstMove.get(toVisit.poll());
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
