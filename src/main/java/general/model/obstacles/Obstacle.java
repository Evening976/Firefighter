package general.model.obstacles;

import util.Position;

public interface Obstacle {
    Position getPosition();
    boolean isObstacle(Position position);

    @Override
    boolean equals(Object o);
    @Override
    int hashCode();
}
