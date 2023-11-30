package model.rockPaperScissors;

import general.model.entities.Entity;
import general.model.obstacles.Obstacle;
import util.Position;

public class Scissors implements RPSObstacle {
    Position p;

    public Scissors(Position position) {
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

    public boolean canConquer(Obstacle opponent) {
        return opponent instanceof Paper;
    }
}
