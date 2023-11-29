package model.firefighterelements.entities.FireFighter;

import general.model.entity.EntityManager;
import util.Position;

import java.util.ArrayList;
import java.util.Set;

public abstract class FireExtinguisher extends EntityManager {
    protected Set<Position> firePositions;
    public FireExtinguisher(Set<Position> firePositions, int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount, initialCount);
        this.firePositions = firePositions;
    }

    protected void extinguish(Position position) {
        firePositions.remove(position);
    }
}
