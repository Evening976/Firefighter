package model.firefighter.obstacle;

import general.model.obstacles.Obstacle;
import util.Position;

public class Road implements Obstacle {
    Position position;
    public Road(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
    @Override
    public boolean isObstacle(Position position) {
        return !position.equals(this.position);
    }
}
