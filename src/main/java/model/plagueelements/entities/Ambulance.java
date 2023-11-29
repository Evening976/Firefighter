package model.plagueelements.entities;

import model.firefighterelements.obstacle.MountainManager;
import model.firefighterelements.obstacle.RoadManager;
import model.plagueelements.DModelElement;
import util.Position;

import java.util.*;

public class Ambulance extends Doctor{
    public Ambulance(Set<Position> plaguePositions, int initialCount, int rowCount, int columnCount) {
        super(plaguePositions, initialCount, rowCount, columnCount);
    }

    @Override
    public Position neighborClosestToFire(Position position, Set<Position> plaguePositions, RoadManager roadManager, MountainManager mountainManager) {
        Position step1 = steps(position, plaguePositions, roadManager, mountainManager);
        return steps(step1, plaguePositions, roadManager, mountainManager);
    }

    private Position steps(Position position, Set<Position> plaguePositions, RoadManager roadManager, MountainManager mountainManager) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));
        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
/*            if (plaguePositions.contains(current) && !mountainManager.isMountain(current))
                return firstMove.get(current);*/
            for (Position adjacent : neighbors(current)) {
/*                if (seen.contains(adjacent) || mountainManager.isMountain(current)) {
                    continue;
                }*/
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    @Override
    public List<DModelElement> getState(Position position) {
        List<DModelElement> result = new ArrayList<>();
        List<Position> ambulancePositions = getPositions();
        for (Position ambulancePosition : ambulancePositions) {
            if (ambulancePosition.equals(position)){
                result.add(DModelElement.AMBULANCE);
            }
        }
        return result;
    }


    @Override
    public void setState(List<DModelElement> state, Position position) {
        List<Position> firefighterPositions = getPositions();
        for (;;) {
            if (!firefighterPositions.remove(position)) break;
        }
        for (DModelElement element : state) {
            if (element.equals(DModelElement.AMBULANCE)) {
                firefighterPositions.add(position);
            }
        }
    }
}
