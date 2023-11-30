package model.rockPaperScissors;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.RPSBoard;


import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import util.Position;

import java.util.*;


public class RockManager extends RPSElement {
    Set<Rock> rocks;
    public RockManager(int initialCount, int rowCount, int columnCount){
        super(initialCount, rowCount, columnCount);
        rocks = new HashSet<>();
        tag = new RPSModelElement(Color.BLACK, "[R]");
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            rocks.add(new Rock(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return new HashSet<>(rocks);
    }

    @Override
    public boolean accept(Position position) {
        for(Obstacle obstacle: rocks) {
            if(obstacle.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        Set<Rock> newRocksPosition = new HashSet<>();
        Set<Position> positionsToRemove = new HashSet<>();

        List<Position> shuffledPositions = new ArrayList<>(getPositions());
        Collections.shuffle(shuffledPositions);

        for (Position rock : shuffledPositions) {
            Position nextPosition = chooseRandomNeighbor(neighbors(rock));

            if (rocks.contains(new Rock(nextPosition))) continue;

            boolean allManagersAccept = managers.stream().allMatch(manager -> manager.accept(nextPosition));
            if (!allManagersAccept) continue;

            newRocksPosition.add(new Rock(nextPosition));
            result.add(nextPosition);

            positionsToRemove.add(rock);
        }

        rocks.addAll(newRocksPosition);

        rocks.removeIf(rock -> positionsToRemove.contains(rock.getPosition()));

        return result;
    }


    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for(Rock rock : rocks) {
            positions.add(rock.getPosition());
        }
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        rocks.removeIf(rock -> rock.getPosition().equals(position));
        for(ModelElement modelElement: state){
            if(modelElement.equals(tag)){
                rocks.add(new Rock(position));
            }
        }
    }
}
