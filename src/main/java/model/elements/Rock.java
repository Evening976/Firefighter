package model.elements;

import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rock extends BoardElement{
    private final List<Position> rockPositions;


    public Rock(List<Position> rockPositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.rockPositions = rockPositions;
        initializeElements(getInitCount());
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.05);
    }


    @Override
    public Collection<Position> getPositions() {
        return rockPositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        rockPositions.clear();
        for(int index = 0; index < initialCount; index++)
            rockPositions.add(new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount)));
    }


    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();
        List<Position> mountainPositions = (List<Position>) getPositions();
        for (Position mountainPosition : mountainPositions) {
            if (mountainPosition.equals(position)){
                result.add(ModelElement.ROCK);
            }
        }
        return result;
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        List<Position> rockPositions = (List<Position>) getPositions();
        for (;;) {
            if (!rockPositions.remove(position)) break;
        }
        for (ModelElement element : state) {
            if (element.equals(ModelElement.ROCK)) {
                rockPositions.add(position);
            }
        }
    }

    public boolean isRock(Position position){
        return rockPositions.contains(position);
    }

}
