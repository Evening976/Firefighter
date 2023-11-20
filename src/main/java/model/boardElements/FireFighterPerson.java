package model.boardElements;

import model.elements.FireFighter;
import model.elements.ModelElement;
import model.elements.Mountain;
import model.elements.Road;
import util.Position;

import java.util.*;
public class FireFighterPerson extends FireFighter {
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
                if (seen.contains(adjacent)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        for(Position position1 : firstMove.keySet()){
            if(mountain.getPositions().contains(position1))
                firstMove.remove(position1);
        }
        return position;
    }


    @Override
    public List<model.elements.ModelElement> getState(Position position) {
        List<model.elements.ModelElement> result = new ArrayList<>();
        List<Position> firefighterPositions = getPositions();
        for (Position firefighterPosition : firefighterPositions) {
            if (firefighterPosition.equals(position)){
                result.add(model.elements.ModelElement.FIREFIGHTERPERSON);
            }
        }
        return result;
    }

    @Override
    public void setState(List<model.elements.ModelElement> state, Position position) {
        List<Position> firefighterPositions = getPositions();
        for (;;) {
            if (!firefighterPositions.remove(position)) break;
        }
        for (model.elements.ModelElement element : state) {
            if (element.equals(ModelElement.FIREFIGHTERPERSON)) {
                firefighterPositions.add(position);
            }
        }
    }
}
