package model.rockPaperScissors;

import general.model.obstacles.ObstacleManager;
import util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
