package model.plagueelement;

import model.firefighterelements.FFModelElement;
import model.firefighterelements.Mountain;
import model.firefighterelements.Road;
import util.Position;

import java.util.*;

public class Doctor extends Medical{

    public Doctor(Set<Position> plaguePositions, int initialCount, int rowCount, int columnCount){
        super(plaguePositions, initialCount, rowCount, columnCount);
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
                if (seen.contains(adjacent) || mountain.isMountain(current)) continue;
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
        List<Position> doctorPositions = getPositions();
        for (Position doctorPosition : doctorPositions) {
            if (doctorPosition.equals(position)){
                result.add(DModelElement.DOCTOR);
            }
        }
        return result;
    }

    @Override
    public void setState(List<DModelElement> state, Position position) {
        List<Position> doctorPositions = getPositions();
        for (;;) {
            if (!doctorPositions.remove(position)) break;
        }
        for (DModelElement element : state) {
            if (element.equals(DModelElement.DOCTOR)) {
                doctorPositions.add(position);
            }
        }
    }
}
