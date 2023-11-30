package model.firefighterelements.obstacle;

import general.model.obstacles.Obstacle;
import util.Position;

public class Mountain implements Obstacle {
    Position position;
    public Mountain(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
    @Override
    public boolean isObstacle(Position position) {
        return position.equals(this.position);
    }
}
