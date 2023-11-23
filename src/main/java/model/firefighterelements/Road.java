package model.firefighterelements;

import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Road extends FFBoardElement {
    private final List<Position> roadPositions;

    public Road(List<Position> roadPositions, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.roadPositions = roadPositions;
        initializeElements(getInitCount());
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.2);
    }


    @Override
    public Collection<Position> getPositions() {
        return roadPositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        roadPositions.clear();
        for(int index = 0; index < initialCount; index++)
            roadPositions.add(new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount)));
    }


    @Override
    public List<FFModelElement> getState(Position position) {
        List<FFModelElement> result = new ArrayList<>();
        List<Position> roadPositions = (List<Position>) getPositions();
        for (Position roadPosition : roadPositions) {
            if (roadPosition.equals(position)){
                result.add(FFModelElement.ROAD);
            }
        }
        return result;
    }

    @Override
    public void setState(List<FFModelElement> state, Position position) {
        List<Position> roadPositions = (List<Position>) getPositions();
        for (;;) {
            if (!roadPositions.remove(position)) break;
        }
        for (FFModelElement element : state) {
            if (element.equals(FFModelElement.ROAD)) {
                roadPositions.add(position);
            }
        }
    }

    public boolean fireCanSpread(Position position){
        return !roadPositions.contains(position);
    }

}
