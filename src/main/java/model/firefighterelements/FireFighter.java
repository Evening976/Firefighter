package model.firefighterelements;

import model.FirefighterBoard;
import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public abstract class FireFighter extends Entity {
    private final Set<Position> firePositions;

    public FireFighter(List<Position> firefighterPositions, Set<Position> firePositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.positions = firefighterPositions;
        this.firePositions = firePositions;
    }

    public FireFighter(Set<Position> firePositions, int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount);
        this.firePositions = firePositions;
        initializeElements(initialCount);
    }

    static Position getPosition(Position position, FirefighterBoard board) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(board.neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (board.isFire(current))
                return firstMove.get(current);
            for (Position adjacent : board.neighbors(current)) {
                if (seen.contains(adjacent) || !board.fireFighterCanMove(current)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
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
            List<Position> neighborFirePositions = board.neighbors(newFirefighterPosition).stream()
                    .filter(firePositions::contains)
                    .toList();
            for (Position firePosition : neighborFirePositions)
                extinguish(firePosition);
            result.addAll(neighborFirePositions);
        }
        positions = firefighterNewPositions;
        return result;
    }


    private void extinguish(Position position) {
        firePositions.remove(position);
    }

    public abstract Position neighborClosestToFire(Position position, FirefighterBoard bord);

    @Override
    public void initializeElements(int initialCount) {
        positions = new ArrayList<>();
        for(int index = 0; index < initialCount; index++)
            positions.add(Position.randomPosition(rowCount, columnCount));
    }
}
