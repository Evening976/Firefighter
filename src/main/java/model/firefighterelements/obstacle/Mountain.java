package model.firefighterelements.obstacle;

import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.List;

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
