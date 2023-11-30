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
    Set<Scissors> scissorsSet;

    public ScissorsManager(int initialCount, int rowCount, int columnCount) {
        super(initialCount, rowCount, columnCount);
        scissorsSet = new HashSet<>();
        tag = new RPSModelElement(Color.BLUE, "[S]");
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            scissorsSet.add(new Scissors(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return new HashSet<>(scissorsSet);
    }

    public boolean accept(Position position) {
        for(Obstacle obstacle: scissorsSet) {
            if(obstacle.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        Set<Scissors> newScissorsPosition = new HashSet<>();
        Set<Position> positionsToRemove = new HashSet<>();

        List<Position> shuffledPositions = new ArrayList<>(getPositions());
        Collections.shuffle(shuffledPositions);

        for (Position scissors : shuffledPositions) {
            Position nextPosition = chooseRandomNeighbor(neighbors(scissors));

            if (scissorsSet.contains(new Scissors(nextPosition))) continue;

            boolean allManagersAccept = managers.stream().allMatch(manager -> manager.accept(nextPosition));
            if (!allManagersAccept) continue;

            newScissorsPosition.add(new Scissors(nextPosition));
            result.add(nextPosition);

            positionsToRemove.add(scissors);
        }

        scissorsSet.addAll(newScissorsPosition);

        // Remove old positions
        scissorsSet.removeIf(scissors -> positionsToRemove.contains(scissors.getPosition()));

        return result;
    }


    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for (Obstacle scissor : scissorsSet) {
            positions.add(scissor.getPosition());
        }
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        scissorsSet.removeIf(scissor -> scissor.getPosition().equals(position));
        for (ModelElement modelElement : state) {
            if (modelElement.equals(tag)) {
                scissorsSet.add(new Scissors(position));
            }
        }
    }
}