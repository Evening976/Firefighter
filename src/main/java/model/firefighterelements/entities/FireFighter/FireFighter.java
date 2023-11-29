package model.firefighterelements.entities.FireFighter;

import general.model.entity.EntityManager;
import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import util.Position;

import java.util.*;

public abstract class FireFighter extends FireExtinguisher {

    List<ObstacleManager> obstacleManagers;

    public FireFighter(Set<Position> firePositions, int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(firePositions, initialCount, rowCount, columnCount);
        this.obstacleManagers = Arrays.asList(obstacleManagers);
    }

    public List<Position> update(FirefighterBoard board) {
        System.out.println("FireFighter update");
        return new ArrayList<>();
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
                if (seen.contains(adjacent) || !obstacleManagers.stream().allMatch(obstacleManager -> obstacleManager.accept(current))) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }
}
