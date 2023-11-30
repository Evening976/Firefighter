package general.model.entities;

import general.model.GameElement;
import util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityManager implements GameElement {
    protected ModelElement tag;
    protected int rowCount;
    protected int columnCount;
    protected int initialCount;

    public EntityManager(int rowCount, int columnCount, int initialCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialCount = initialCount;
    }

    protected List<Position> neighbors(Position position){
        List<Position> list = new ArrayList<>();
        if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
        if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
        if (position.row() < rowCount - 1) list.add(new Position(position.row() + 1, position.column()));
        if (position.column() < columnCount - 1) list.add(new Position(position.row(), position.column() + 1));
        return list;
    }

    public abstract void initializeElements();
    public ModelElement getState(Position position){
        if(getPositions().contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
    }

    public abstract void setState(List< ? extends ModelElement> state, Position position);
}
