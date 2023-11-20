package model.boardElements;

import model.elements.BoardElement;
import model.elements.ModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mountain extends BoardElement {
    private final List<Position> mountainPositions;


    public Mountain(List<Position> mountainPositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.mountainPositions = mountainPositions;
        initializeElements(getInitCount());
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.2);
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
    public List<model.elements.ModelElement> getState(Position position) {
        List<model.elements.ModelElement> result = new ArrayList<>();
        List<Position> mountainPositions = (List<Position>) getPositions();
        for (Position mountainPosition : mountainPositions) {
            if (mountainPosition.equals(position)){
                result.add(model.elements.ModelElement.MOUNTAIN);
            }
        }
        return result;
    }

    @Override
    public void setState(List<model.elements.ModelElement> state, Position position) {
        List<Position> mountainPositions = (List<Position>) getPositions();
        for (;;) {
            if (!mountainPositions.remove(position)) break;
        }
        for (model.elements.ModelElement element : state) {
            if (element.equals(ModelElement.MOUNTAIN)) {
                mountainPositions.add(position);
            }
        }
    }

    public boolean isMountain(Position position){
        return mountainPositions.contains(position);
    }
    public boolean fireCanSpread(Position position){
        return !mountainPositions.contains(position);
    }

    public boolean isCrossable(Position position){
        return !mountainPositions.contains(position);
    }
}
