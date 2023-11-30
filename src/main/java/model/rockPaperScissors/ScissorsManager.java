package model.rockPaperScissors;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import util.Position;
import model.RPSBoard;

import java.util.*;

public class ScissorsManager extends RPSElement {
    Set<Scissors> scissors;

    public ScissorsManager(int initialCount, int rowCount, int columnCount) {
        super(initialCount, rowCount, columnCount);
        scissors = new HashSet<>();
        tag = new RPSModelElement(Color.BLUE, "[S]");
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            scissors.add(new Scissors(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return new HashSet<>(scissors);
    }

    public boolean accept(Position position) {
        for(Obstacle obstacle: scissors) {
            if(obstacle.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        Set<Scissors> scissorsPosition = new HashSet<>();
        for (Position scissor : getPositions()) {
            for (Position nextPosition : neighbors(scissor)) {
                if (getPositions().contains(nextPosition)) continue;
                if (!managers.stream().allMatch(manager -> manager.accept(nextPosition))) continue;
                scissorsPosition.add(new Scissors(nextPosition));
                result.add(nextPosition);
            }
        }
        scissors.addAll(scissorsPosition);
        return result;
    }

    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for (Obstacle scissor : scissors) {
            positions.add(scissor.getPosition());
        }
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        scissors.removeIf(scissor -> scissor.getPosition().equals(position));
        for (ModelElement modelElement : state) {
            if (modelElement.equals(tag)) {
                scissors.add(new Scissors(position));
            }
        }
    }
}