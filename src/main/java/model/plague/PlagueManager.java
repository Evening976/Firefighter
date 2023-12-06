package model.plague;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.PlagueModelE;
import util.Position;
import util.RandomGenerator;

import java.util.*;

public class PlagueManager extends EntityManager {

    List<ObstacleManager> obstacleManagers;
    Set<People> plagued;
    public PlagueManager(int rowCount, int columnCount, int initialCount, ObstacleManager... obstacleManagers) {
        super(rowCount, columnCount, initialCount);
        this.tag = new PlagueModelE(Color.GREEN, "[C]");
        plagued = new HashSet<>();

        if(obstacleManagers.length == 0) this.obstacleManagers = Arrays.asList(new ObstacleManager[0]);
        else this.obstacleManagers = Arrays.asList(obstacleManagers);

        initializeElements();
    }

    public List<Position> update(PopulationManager populationManager){
        List<Position> result = new ArrayList<>();
        return result;

/*        if(populationManager.getPositions().isEmpty()) return result;

        Set<People> plaguedNewPositions = new HashSet<>();
        for (Position infectedPositions : getPositions()) {
            Position newPlaguedPosition = moveRandomly(infectedPositions);
            populationManager.infect(newPlaguedPosition);
            plaguedNewPositions.add(new People(newPlaguedPosition, true));
            result.add(infectedPositions);
            result.add(newPlaguedPosition);
            List<Position> neighborPeople = neighbors(newPlaguedPosition);
            for (Position guysPosition : neighborPeople) {
                populationManager.infect(guysPosition);
            }
            result.addAll(neighborPeople);
        }
        plagued.clear();
        plagued.addAll(plaguedNewPositions);
        return result;*/
    }

    @Override
    public Collection<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (People p : plagued) {
            positions.add(p.getPosition());
        }
        return positions;
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            plagued.add(new People(Position.randomPosition(rowCount, columnCount), true));
        }
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        plagued.removeIf(plaguedboi -> plaguedboi.getPosition().equals(position));
        for(ModelElement element: state) {
            if (element.equals(tag)) {
                plagued.add(new People(position, true));
            }
        }
    }

    protected Position neighborClosestToVirusedDude(Position position, PopulationManager populationManager) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (populationManager.getPositions().contains(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors(current)) {
                if (seen.contains(adjacent)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    public Position moveRandomly(Position position) {
        int row = position.row();
        int column = position.column();
        int random = RandomGenerator.randomInt(0, 3);
        switch (random) {
            case 0 -> {
                if (row > 0) return new Position(row - 1, column);
                else return new Position(row + 1, column);
            }
            case 1 -> {
                if (column > 0) return new Position(row, column - 1);
                else return new Position(row, column + 1);
            }
            case 2 -> {
                if (row < rowCount - 1) return new Position(row + 1, column);
                else return new Position(row - 1, column);
            }
            case 3 -> {
                if (column < columnCount - 1) return new Position(row, column + 1);
                else return new Position(row, column - 1);
            }
        }
        return position;
    }
}
