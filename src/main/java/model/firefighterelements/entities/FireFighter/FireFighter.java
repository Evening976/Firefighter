package model.firefighterelements.entities.FireFighter;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

public abstract class FireFighter extends FireExtinguisher{

    public FireFighter(Set<Position> firePositions, int initialCount, int rowCount, int columnCount){
        super(firePositions, initialCount, rowCount, columnCount);
    }

    public List<Position> update(FirefighterBoard board) {
        List<Position> result = new ArrayList<>();
        List<Position> firefighterNewPositions = new ArrayList<>();
        for (Position firefighterPosition : getPositions()) {
            Position newFirefighterPosition = neighborClosestToFire(firefighterPosition, board);
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
        positions = firefighterNewPositions;
        return result;
    }

    protected Position neighborClosestToFire(Position position, FirefighterBoard board) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (board.isFire(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors(current)) {
                if (seen.contains(adjacent) || !board.fireFighterCanMove(current)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }
}
