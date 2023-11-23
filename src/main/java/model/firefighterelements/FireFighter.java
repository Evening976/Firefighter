package model.firefighterelements;

import model.Board;
import model.FirefighterBoard;
import util.Position;

import java.util.*;

import static util.RandomGenerator.randomPosition;

public abstract class FireFighter extends FFBoardElement {
    private List<Position> firefighterPositions;
    private final Set<Position> firePositions;

    public FireFighter(List<Position> firefighterPositions, Set<Position> firePositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.firefighterPositions = firefighterPositions;
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
        for (Position firefighterPosition : firefighterPositions) {
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
        firefighterPositions = firefighterNewPositions;
        return result;
    }


    private void extinguish(Position position) {
        firePositions.remove(position);
    }

    public abstract Position neighborClosestToFire(Position position, FirefighterBoard bord);

    @Override
    public void initializeElements(int initialCount) {
        firefighterPositions = new ArrayList<>();
        for(int index = 0; index < initialCount; index++)
            firefighterPositions.add(randomPosition(rowCount, columnCount));
    }


    public abstract List<FFModelElement> getState(Position position);
    public abstract void setState(List<FFModelElement> state, Position position);

    @Override
    public List<Position> getPositions() {
        return firefighterPositions;
    }

}
