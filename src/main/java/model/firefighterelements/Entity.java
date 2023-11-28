package model.firefighterelements;

import general.model.entity.ModelElement;
import util.Position;

import java.util.Collection;
import java.util.List;

public abstract class Entity {
    protected Collection<Position> positions;
    protected FFModelElement tag;
    protected int rowCount;
    protected int columnCount;
    protected int initialCount;
    public Entity(int rowCount, int columnCount, int initialCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialCount = initialCount;
    }

    public Collection<Position> getPositions(){
        return positions;
    }
    public void initializeElements(){
        positions.clear();
        for(int index = 0; index < initialCount; index++)
            positions.add(Position.randomPosition(rowCount, columnCount));
    }
    public ModelElement getState(Position position){
        if(getPositions().contains(position)) return tag;
        return ModelElement.EMPTY;
    }
    public void setState(List< ? extends ModelElement> state, Position position){
        List<Position> entityPositions = (List<Position>) getPositions();
        for(;;){
            if(!entityPositions.remove(position)) break;
        }
        for(ModelElement element: state){
            if(element.equals(tag)){
                entityPositions.add(position);
            }
        }
    }
}
