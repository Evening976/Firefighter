package model;

import general.model.GameElement;
import general.model.entity.ModelElement;
import model.Board;
import model.rockPaperScissors.*;
import util.Position;

import java.util.ArrayList;
import java.util.List;

public class RPSBoard implements Board<List<RPSModelElement>> {
    private final int columnCount;
    private final int rowCount;
    private int step = 0;
    private RockManager rockManager;
    private PaperManager paperManager;
    private ScissorsManager scissorsManager;

    public RPSBoard(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;

        initializeElements();
    }

    public void initializeElements() {
        rockManager = new RockManager(5, rowCount, columnCount);
        paperManager = new PaperManager(5, rowCount, columnCount);
        scissorsManager = new ScissorsManager(5, rowCount, columnCount);
    }

    public int rowCount() {
        return rowCount;
    }

    public int columnCount() {
        return columnCount;
    }

    public List<Position> updateToNextGeneration() {
        List<Position> result = new ArrayList<>();
        result.addAll(rockManager.update(this));
        result.addAll(paperManager.update(this));
        result.addAll(scissorsManager.update(this));

        step++;

        return result;
    }

    @Override
    public void reset() {
        step = 0;
        initializeElements();
    }

    @Override
    public int stepNumber() {
        return step;
    }


    public List<RPSModelElement> getState(Position position) {
        List<RPSModelElement> result = new ArrayList<>();

        for (GameElement element : getGameElements()) {
            ModelElement e = element.getState(position);
            if (e instanceof RPSModelElement) result.add((RPSModelElement) element.getState(position));
            else result.add(RPSModelElement.EMPTY);
        }

        return result;
    }

    List<GameElement> getGameElements() {
        List<GameElement> result = new ArrayList<>();
        result.add(rockManager);
        result.add(paperManager);
        result.add(scissorsManager);
        return result;
    }

    public void setState(List<RPSModelElement> state, Position position) {
        for (GameElement element : getGameElements()) {
            element.setState(state, position);
        }
    }


    public boolean rockCanConquer(Position nextPosition) {
        return !paperManager.getPositions().contains(nextPosition);
    }

    public boolean scissorsCanConquer(Position nextPosition) {
        return !rockManager.getPositions().contains(nextPosition);
    }

    public boolean paperCanConquer(Position nextPosition) {
        return !scissorsManager.getPositions().contains(nextPosition);
    }
}
