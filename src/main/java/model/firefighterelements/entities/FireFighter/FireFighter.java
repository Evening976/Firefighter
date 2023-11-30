package model.firefighterelements.entities.FireFighter;

import general.model.entity.EntityManager;
import general.model.obstacle.ObstacleManager;
import model.FirefighterBoard;
import model.firefighterelements.entities.Fire;
import model.firefighterelements.entities.FireManager;
import util.Position;

import java.util.*;

public abstract class FireFighter extends EntityManager {

    List<ObstacleManager> obstacleManagers;

    public FireFighter(int initialCount, int rowCount, int columnCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);
        this.obstacleManagers = Arrays.asList(obstacleManagers);
    }

    public abstract List<Position> update(FireManager fireManager);

    protected Position neighborClosestToFire(Position position, FireManager fireManager) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (fireManager.getPositions().contains(current))
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
