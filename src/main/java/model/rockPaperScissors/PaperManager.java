package model.rockPaperScissors;

import general.model.entity.EntityManager;
import general.model.entity.ModelElement;
import util.Position;
import model.RPSBoard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaperManager extends EntityManager {
    Set<Paper> papers;

    public PaperManager(int initialCount, int rowCount, int columnCount) {
        super(rowCount, columnCount, initialCount);
        papers = new HashSet<>();
        tag = RPSModelElement.PAPER;
        initializeElements();
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            papers.add(new Paper(Position.randomPosition(rowCount, columnCount)));
        }
    }

    public List<Position> update(RPSBoard board) {
        List<Position> result = new ArrayList<>();
        List<Position> papersPosition = new ArrayList<>();
        for (Position paper : getPositions()) {
            for (Position nextPosition : neighbors(paper)) {
                if (papers.contains(new Paper(nextPosition))) continue;

                if (board.paperCanConquer(nextPosition)) {
                    papersPosition.add(nextPosition);
                }
            }
        }
        for (Position position : papersPosition) {
            papers.add(new Paper(position));
        }
        result.addAll(papersPosition);
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
    public ModelElement getState(Position position) {
        if (getPositions().contains(position)) {
            return tag;
        }
        return ModelElement.EMPTY;
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