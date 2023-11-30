package model.rockPaperScissors;

import general.model.entities.Entity;
import general.model.obstacles.Obstacle;
import util.Position;

public class Paper implements Obstacle {
    Position p;
    public Paper(Position position) {
        this.p = position;
    }

    @Override
    public Position getPosition() {
        return p;
    }

    @Override
    public boolean isObstacle(Position position) {
        return p.equals(position);
    }
}
