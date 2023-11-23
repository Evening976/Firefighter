package model.plagueelements;
import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DBoardElement {
    protected int rowCount;
    protected int columnCount;

    public DBoardElement(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    protected List<Position> neighbors(Position position){
        List<Position> list = new ArrayList<>();
        if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
        if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
        if (position.row() < rowCount - 1) list.add(new Position(position.row() + 1, position.column()));
        if (position.column() < columnCount - 1) list.add(new Position(position.row(), position.column() + 1));
        return list;
    }

    protected boolean isClear(Position position, Collection<Position> firePositions,
                              Collection<Position> firefighterPositions,
                              Collection<Position> cloudPositions,
                              Collection<Position> mountainPositions,
                              Collection<Position> roadPositions,
                              Collection<Position> rockPositions
    ) {
        return !firePositions.contains(position)
                && !firefighterPositions.contains(position)
                && !cloudPositions.contains(position)
                && !mountainPositions.contains(position)
                && !roadPositions.contains(position)
                && !rockPositions.contains(position);
    }

    public abstract Collection<Position> getPositions();
    public abstract void initializeElements(int initialCount);
    public abstract List<DModelElement> getState(Position position);
    public abstract void setState(List<DModelElement> state, Position position);
}
