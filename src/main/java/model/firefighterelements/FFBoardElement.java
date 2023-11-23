package model.firefighterelements;

import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class FFBoardElement {
    protected int rowCount;
    protected int columnCount;

    public FFBoardElement(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
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
    public abstract List<FFModelElement> getState(Position position);
    public abstract void setState(List<FFModelElement> state, Position position);
}
