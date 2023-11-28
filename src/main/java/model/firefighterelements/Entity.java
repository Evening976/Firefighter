package model.firefighterelements;

import util.Position;

import java.util.Collection;
import java.util.List;

public abstract class Entity {
    Collection<Position> positions;
    FFModelElement tag;
    protected int rowCount;
    protected int columnCount;

    public Entity(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public Collection<Position> getPositions(){
        return positions;
    }
    public void initializeElements(int initialCount){
        positions.clear();
        for(int index = 0; index < initialCount; index++)
            positions.add(Position.randomPosition(rowCount, columnCount));
    }
    public FFModelElement getState(Position position){
        if(getPositions().contains(position)) return tag;
        return FFModelElement.EMPTY;
    }
    public void setState(List<FFModelElement> state, Position position){
        List<Position> entityPositions = (List<Position>) getPositions();
        for(;;){
            if(!entityPositions.remove(position)) break;
        }
        for(FFModelElement element: state){
            if(element.equals(tag)){
                entityPositions.add(position);
            }
        }
    }
}
