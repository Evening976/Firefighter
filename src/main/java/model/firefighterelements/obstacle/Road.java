package model.firefighterelements.obstacle;

import model.firefighterelements.Entity;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.ArrayList;

public class Road extends Entity {
    public Road(int rowCount, int columnCount) {
        super(rowCount, columnCount, (int) (rowCount*columnCount*0.2));
        this.positions = new ArrayList<>();
        tag = FFModelElement.ROAD;
        initialCount = getInitCount();
        initializeElements();
    }

    public int getInitCount(){
        int totalCells = rowCount * columnCount;
        return (int) (totalCells * 0.2);
    }

    public boolean fireCanSpread(Position position){
        return !positions.contains(position);
    }
}
