package model.plague;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.PlagueModelE;
import util.Position;
import util.RandomGenerator;

import java.util.*;

public class PopulationManager extends EntityManager {

    Set<People> population;
    List<ObstacleManager> obstacleManagers;
    int normalCount;
    int plaguedCount;

    public PopulationManager(int rowCount, int columnCount, int normalCount, int plaguedCount, ObstacleManager... obstacleManagers) {
        super(rowCount, columnCount, normalCount + plaguedCount);
        population = new HashSet<>();
        tag = new PlagueModelE(Color.YELLOW, "[P]");
        this.normalCount = normalCount;
        this.plaguedCount = plaguedCount;

        if(obstacleManagers.length == 0) this.obstacleManagers = Arrays.asList(new ObstacleManager[0]);
        else this.obstacleManagers = Arrays.asList(obstacleManagers);

        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < normalCount; index++) {
            population.add(new People(Position.randomPosition(rowCount, columnCount), false));
        }
        for (int index = 0; index < plaguedCount; index++) {
            population.add(new People(Position.randomPosition(rowCount, columnCount), true));
        }
    }

    public void cure(Position position) {
        Set<People> newPopulation = new HashSet<>(population);
        for(People guy: population){
            if(guy.getPosition().equals(position) && guy.isInfected() && guy.isAlive()){
                guy.setInfected(false);
            }
        }
        population.clear();
        population = newPopulation;
    }

    public List<Position> update(int step){
        List<Position> result = new ArrayList<>();
        Set<People> newPopulation = new HashSet<>();

        for(People guy: population){
            guy.updateStep();
            if(!guy.isAlive()){
                newPopulation.remove(guy);
            }
        }

        for (Position infectedPosition : getInfectedPositions()) {
            Position newPlaguedPosition = moveRandomly(infectedPosition);
            infect(newPlaguedPosition);
            newPopulation.add(new People(newPlaguedPosition, true));
            result.add(infectedPosition);
            result.add(newPlaguedPosition);
            List<Position> neighborPeople = neighbors(newPlaguedPosition);
            for (Position guysPosition : neighborPeople) {
                infect(guysPosition);
            }
            result.addAll(neighborPeople);
        }

        population.clear();
        population = newPopulation;

        return result;
    }

    public void infect(Position position){
        for(People guy: population){
            if(guy.getPosition().equals(position) && !guy.isInfected()){
                guy.setInfected(true);
            }
        }
    }

    @Override
    public Collection<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for(People guy: population) {
            positions.add(guy.getPosition());
        }
        return positions;
    }

    public Collection<Position> getInfectedPositions() {
        Set<Position> positions = new HashSet<>();
        for(People guy: population) {
            if(guy.isInfected()) positions.add(guy.getPosition());
        }
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        population.removeIf(guy -> guy.getPosition().equals(position));
        for(ModelElement modelElement: state){
            if(modelElement.equals(tag)){
                population.add(new People(position));
            }
        }
    }

    @Override
    public ModelElement getState(Position position) {
        for (People p : population) {
            if (p.getPosition().equals(position) && p.infected) return new PlagueModelE(Color.GREEN, "[C]");
            if (p.getPosition().equals(position) && !p.infected) return new PlagueModelE(Color.YELLOW, "[P]");
        }
        return ModelElement.EMPTY;
    }

    public boolean isInfected(Position position){
        for(People guy: population){
            if(guy.getPosition().equals(position) && guy.isInfected()) return true;
        }
        return false;
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
