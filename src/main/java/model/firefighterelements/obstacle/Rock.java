package model.firefighterelements.obstacle;

import general.model.entity.ModelElement;
import general.model.obstacle.Obstacle;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.List;

public class Rock implements Obstacle {
    int spreadStep = 2;
    Position position;
    public Rock(Position position) {
        this.position = position;
    }
    public Position getPosition() {
        return position;
    }
    @Override
    public boolean isObstacle(Position position) {
        if(spreadStep == 0){
            spreadStep = 2;
            return true;
        }
        spreadStep--;
        return false;
    }

}
