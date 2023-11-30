package model.rockPaperScissors;

import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import model.RPSBoard;
import util.Position;
import util.RandomGenerator;

import java.util.*;

public abstract class RPSElement extends ObstacleManager {
    int initialCount;
    List<ObstacleManager> managers;
    protected RPSElement(int initialCount, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.initialCount = initialCount;
        managers = new ArrayList<>();
    }


    public void addManager(ObstacleManager manager) {
        managers.add(manager);
    }

    protected List<Position> neighbors(Position position){
        List<Position> list = new ArrayList<>();
        if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
        if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
        if (position.row() < rowCount - 1) list.add(new Position(position.row() + 1, position.column()));
        if (position.column() < columnCount - 1) list.add(new Position(position.row(), position.column() + 1));
        return list;
    }

    public Obstacle getObstacleAtPosition(Position position) {
        for(Obstacle obstacle: getObstacles()) {
            if(obstacle.getPosition().equals(position)) {
                return obstacle;
            }
        }
        return null;
    }


    public Position chooseRandomNeighbor(List<Position> neighbors) {
        Collections.shuffle(neighbors);
        if(neighbors.isEmpty()) return null;
        return neighbors.get(0);

    }

    public abstract Collection<Position> update(RPSBoard rpsBoard);

}
