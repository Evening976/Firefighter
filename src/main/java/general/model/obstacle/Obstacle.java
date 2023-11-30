package general.model.obstacle;

import general.model.entity.ModelElement;
import util.Position;

import java.util.List;

public interface Obstacle {
    Position getPosition();
    boolean isObstacle(Position position);

    @Override
    boolean equals(Object o);
    @Override
    int hashCode();
}
