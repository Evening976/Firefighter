package model.elements;

import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public class FireFighter extends BoardElement {
    private List<Position> firefighterPositions;
    private final Set<Position> firePositions;

    public FireFighter(List<Position> firefighterPositions, Set<Position> firePositions, int rowCount, int columnCount) {
        this.firefighterPositions = firefighterPositions;
        this.firePositions = firePositions;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public FireFighter(Set<Position> firePositions, int initialCount, int rowCount, int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.firePositions = firePositions;
        initializeElements(initialCount);
    }

    public List<Position> update(Set<Position> firePositions) {
        List<Position> result = new ArrayList<>();
        List<Position> firefighterNewPositions = new ArrayList<>();
        for (Position firefighterPosition : firefighterPositions) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, firePositions);
            firefighterNewPositions.add(newFirefighterPosition);
            extinguish(newFirefighterPosition);
            result.add(firefighterPosition);
            result.add(newFirefighterPosition);
            List<Position> neighborFirePositions = neighbors(newFirefighterPosition).stream()
                    .filter(firePositions::contains)
                    .toList();
            for (Position firePosition : neighborFirePositions)
                extinguish(firePosition);
            result.addAll(neighborFirePositions);
        }
        firefighterPositions = firefighterNewPositions;
        return result;
    }


    private void extinguish(Position position) {
        firePositions.remove(position);
    }

    private Position neighborClosestToFire(Position position, Set<Position> firePositions) {
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
                if (seen.contains(adjacent)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    @Override
    public void initializeElements(int initialCount) {
        firefighterPositions = new ArrayList<>();
        for(int index = 0; index < initialCount; index++)
            firefighterPositions.add(randomPosition(rowCount, columnCount));
    }

    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        for (Position firefighterPosition : firefighterPositions) {
            if (firefighterPosition.equals(position)){
                result.add(ModelElement.FIREFIGHTER);
            }
        }
        return result;
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        for (;;) {
            if (!firefighterPositions.remove(position)) break;
        }
        for (ModelElement element : state) {
            if (element.equals(ModelElement.FIREFIGHTER)) {
                firefighterPositions.add(position);
            }
        }
    }

    @Override
    public List<Position> getPositions() {
        return firefighterPositions;
    }
}