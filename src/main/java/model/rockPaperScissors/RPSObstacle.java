package model.rockPaperScissors;

import general.model.obstacles.Obstacle;
import util.Position;

public interface RPSObstacle extends general.model.obstacles.Obstacle{

    boolean canConquer(Obstacle obstacleAtPosition);

}
