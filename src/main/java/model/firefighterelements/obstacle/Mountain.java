package model.firefighterelements.obstacle;

import model.firefighterelements.Entity;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mountain extends Entity {
    public Mountain(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.positions = new ArrayList<>();
        tag = FFModelElement.MOUNTAIN;
        initializeElements(getInitCount());
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.2);
    }

    @Override
    public void initializeElements(int initialCount) {
        positions.clear();
        for(int index = 0; index < initialCount; index++)
            positions.add(new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount)));
    }


    public boolean isMountain(Position position){
        return positions.contains(position);
    }
    public boolean fireCanSpread(Position position){
        return !positions.contains(position);
    }

    public boolean isCrossable(Position position){
        return !positions.contains(position);
    }
}
