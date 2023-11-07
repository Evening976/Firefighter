package model.elements;

import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mountain extends BoardElement{
    private final List<Position> mountainPositions;


    public Mountain(List<Position> mountainPositions, int rowCount, int columnCount) {
        this.mountainPositions = mountainPositions;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        initializeElements(getInitCount());
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.3);
    }


    @Override
    public Collection<Position> getPositions() {
        return mountainPositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        mountainPositions.clear();
        for(int index = 0; index < initialCount; index++)
            mountainPositions.add(new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount)));
    }


    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        List<Position> mountainPositions = (List<Position>) getPositions();
        for (Position mountainPosition : mountainPositions) {
            if (mountainPosition.equals(position)){
                result.add(ModelElement.MOUNTAIN);
            }
        }
        return result;
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        List<Position> mountainPositions = (List<Position>) getPositions();
        for (;;) {
            if (!mountainPositions.remove(position)) break;
        }
        for (ModelElement element : state) {
            if (element.equals(ModelElement.MOUNTAIN)) {
                mountainPositions.add(position);
            }
        }
    }

    public boolean fireCanSpread(Position position){
        return !mountainPositions.contains(position);
    }

    public boolean isCrossable(Position position){
        return !mountainPositions.contains(position);
    }
}
