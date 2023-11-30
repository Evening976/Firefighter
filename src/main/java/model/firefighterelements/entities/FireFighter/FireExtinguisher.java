package model.firefighterelements.entities.FireFighter;

import general.model.entity.EntityManager;
import util.Position;

import java.util.ArrayList;
import java.util.Set;

public abstract class FireExtinguisher extends EntityManager {
    public FireExtinguisher(int initialCount, int rowCount, int columnCount){
        super(rowCount, columnCount, initialCount);
    }
}
