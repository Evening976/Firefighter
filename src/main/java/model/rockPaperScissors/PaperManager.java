package model.rockPaperScissors;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.Obstacle;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import util.Position;
import model.RPSBoard;

import java.util.*;

public class PaperManager extends RPSElement {
    Set<Paper> papers;
    public PaperManager(int initialCount, int rowCount, int columnCount) {
        super(initialCount, rowCount, columnCount);
        papers = new HashSet<>();
        tag = new RPSModelElement(Color.YELLOW, "[P]");
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            papers.add(new Paper(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public Set<Obstacle> getObstacles() {
        return new HashSet<>(papers);
    }

    @Override
    public boolean accept(Position position) {
        for(Obstacle obstacle: papers) {
            if(obstacle.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        Set<Paper> newPapersPosition = new HashSet<>();
        for (Position paper : getPositions()) {
            for (Position nextPosition : neighbors(paper)) {
                if (papers.contains(new Paper(nextPosition))) continue;
                if (!managers.stream().allMatch(manager -> manager.accept(nextPosition))) continue;
                newPapersPosition.add(new Paper(nextPosition));
                result.add(nextPosition);
            }
        }
        papers.addAll(newPapersPosition);
        return result;
    }

    @Override
    public Set<Position> getPositions() {
        Set<Position> positions = new HashSet<>();
        for (Paper paper : papers) {
            positions.add(paper.getPosition());
        }
        return positions;
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        papers.removeIf(paper -> paper.getPosition().equals(position));
        for (ModelElement modelElement : state) {
            if (modelElement.equals(tag)) {
                papers.add(new Paper(position));
            }
        }
    }
}