package model.plagueelements.entities;

import model.firefighterelements.obstacle.Mountain;
import model.firefighterelements.obstacle.Road;
import model.firefighterelements.obstacle.Rock;
import model.plagueelements.DBoardElement;
import model.plagueelements.DModelElement;
import util.Position;

import java.util.*;

public class Plague extends DBoardElement {
    private Set<Position> plaguePositions;
    private int step;

    public Plague(Set<Position> plaguePositions, int step, int rowCount, int columnCount) {
        super(rowCount, columnCount);
        this.plaguePositions = plaguePositions;
        this.step = step;

    }

    public Plague(int initialCount, int step, int rowCount, int columnCount){
        super(rowCount, columnCount);
        this.step = step;
        initializeElements(initialCount);
    }

    public void updateStep(int step){
        this.step = step;
    }


    public List<Position> update(Road road, Mountain mountain, Rock rock) {
        List<Position> result = new ArrayList<>();
        if (step % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            for (Position fire : plaguePositions) {
                List<Position> fireNeighbors = neighbors(fire);
                for (Position neighbor : fireNeighbors) {
                    if (road.fireCanSpread(neighbor) && mountain.fireCanSpread(neighbor)) {
                        if (rock.isRock(neighbor)) {
                            if (!plaguePositions.contains(neighbor) && step % 4 == 0 && step != 0) {
                                newFirePositions.add(neighbor);
                            }
                        } else {
                            newFirePositions.add(neighbor);
                        }
                    }
                }
            }
            plaguePositions.addAll(newFirePositions);
            result.addAll(newFirePositions);
        }
        return result;
    }


    @Override
    public Set<Position> getPositions() {
        return plaguePositions;
    }

    @Override
    public void initializeElements(int initialCount) {
        plaguePositions = new HashSet<>();
        for (int index = 0; index < initialCount; index++) {
            plaguePositions.add(Position.randomPosition(rowCount, columnCount));
        }
    }

    @Override
    public List<DModelElement> getState(Position position) {
        List<DModelElement> result = new ArrayList<>();
        if (plaguePositions.contains(position))
            result.add(DModelElement.PLAGUE);
        return result;
    }

    @Override
    public void setState(List<DModelElement> state, Position position) {
        plaguePositions.remove(position);
        for(DModelElement element : state) {
            if (element.equals(DModelElement.PLAGUE)) {
                plaguePositions.add(position);
            }
        }
    }
}
