package general.model.obstacle;

import java.util.Collection;
import java.util.Set;

import general.model.GameElement;
import general.model.entity.ModelElement;
import model.firefighterelements.FFModelElement;
import util.Position;

public abstract class ObstacleManager implements GameElement {
    protected FFModelElement tag;
    protected int rowCount;
    protected int columnCount;
    protected ObstacleManager(int rowCount, int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }
    public abstract void initializeElements();
    public abstract Set<Obstacle> getObstacles();
    public abstract boolean accept(Position position);
    public boolean contains(Position position){
        for(Obstacle obstacle: getObstacles())
            if(obstacle.getPosition().equals(position)) return true;
        return false;
    }
}
