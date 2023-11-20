package model.boardElements;

import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class AbstractBoardElement implements ModelElement {
    protected int rowCount;
    protected int columnCount;

    public AbstractBoardElement(int rowCount, int columnCount) {
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


    public abstract Collection<Position> getPositions();

    public abstract void initializeElements(int initialCount);

    public abstract ModelElement getState(Position position);

    public abstract void setState(ModelElement state, Position position);


}

