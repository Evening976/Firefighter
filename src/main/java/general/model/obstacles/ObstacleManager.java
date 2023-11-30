package general.model.obstacles;

import java.util.Set;

import general.model.GameElement;
import general.model.entities.ModelElement;
import util.Position;

public abstract class ObstacleManager implements GameElement {
    protected ModelElement tag;
    protected int rowCount;
    protected int columnCount;
    protected ObstacleManager(int rowCount, int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }
    public abstract void initializeElements();
    public abstract Set<Obstacle> getObstacles();
    public abstract boolean accept(Position position);
    public ModelElement getState(Position position){
        if(contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
    }
    public boolean contains(Position position){
        for(Obstacle obstacle: getObstacles())
            if(obstacle.getPosition().equals(position)) return true;
        return false;
    }
}
