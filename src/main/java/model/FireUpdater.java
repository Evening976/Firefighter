package model;

import util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class FireUpdater implements GameElement {
    private final Set<Position> firePositions;
    private int step;
    private final int rowCount;
    private final int columnCount;

    public FireUpdater(Set<Position> firePositions, int step, int rowCount, int columnCount) {
        this.firePositions = firePositions;
        this.step = step;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public List<Position> neighbors(Position position) {
        List<Position> list = new ArrayList<>();
        if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
        if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
        if (position.row() < rowCount - 1) list.add(new Position(position.row() + 1, position.column()));
        if (position.column() < columnCount - 1) list.add(new Position(position.row(), position.column() + 1));
        return list;
    }


    public List<Position> update() {
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : firePositions) {
                newFirePositions.addAll(neighbors(fire));
            }
            firePositions.addAll(newFirePositions);
            result.addAll(newFirePositions);
        }
        return result;
    }

}


