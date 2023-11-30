package general.model.obstacle;

import java.util.Collection;
import java.util.Set;

import general.model.GameElement;
import general.model.entity.ModelElement;
import util.Position;

public abstract class ObstacleManager implements GameElement {
    public abstract void initializeElements(int rowCount, int colCount);
    public abstract Set<Obstacle> getObstacles();
    public abstract boolean accept(Position position);
    public boolean contains(Position position){
        for(Obstacle obstacle: getObstacles())
            if(obstacle.getPosition().equals(position)) return true;
        return false;
    }
}
