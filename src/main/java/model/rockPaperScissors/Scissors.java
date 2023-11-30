package model.rockPaperScissors;

import general.model.entities.Entity;
import general.model.obstacles.Obstacle;
import util.Position;

public class Scissors implements Obstacle {
    private Position position;
    public Scissors(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean isObstacle(Position position) {
        return this.position.equals(position);
    }
}
