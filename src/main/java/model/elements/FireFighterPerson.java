package model.elements;

import util.Position;

import java.util.*;

public class FireFighterPerson extends FireFighter{
    public FireFighterPerson(Set<Position> firePositions, int initialCount, int rowCount, int columnCount) {
        super(firePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, Set<Position> firePositions, Road road, Mountain mountain) {
        if (position == null) {
            System.out.println("Error: Null position passed to neighborClosestToFire");
            return position;
        }

        Set<Position> seen = new HashSet<>();
        Queue<Position> toVisit = new LinkedList<>();
        HashMap<Position, Position> firstMove = new HashMap<>();

        toVisit.add(position);
        firstMove.put(position, position);

        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();

            if (firePositions.contains(current)) {
                return firstMove.get(current);
            }

            for (Position adjacent : neighbors(current)) {
                if (!seen.contains(adjacent) && mountain.isCrossable(adjacent) && road.isCrossable(adjacent)) {
                    toVisit.add(adjacent);
                    seen.add(adjacent);
                    firstMove.put(adjacent, firstMove.get(current));
                }
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
                result.add(ModelElement.FIREFIGHTERPERSON);
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
            if (element.equals(ModelElement.FIREFIGHTERPERSON)) {
                firefighterPositions.add(position);
            }
        }
    }
}
