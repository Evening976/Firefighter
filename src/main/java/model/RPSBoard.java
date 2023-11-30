package model;

import general.model.GameElement;
import general.model.entities.ModelElement;
import model.rockPaperScissors.RPSObstacle;
import javafx.util.Pair;
import model.firefighterelements.FFModelElement;
import model.rockPaperScissors.*;
import util.Position;
import view.ViewElement;

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
        paperManager = new PaperManager(5, rowCount, columnCount);
        rockManager = new RockManager(5, rowCount, columnCount);
        scissorsManager = new ScissorsManager(5, rowCount, columnCount);

        paperManager.addManager(scissorsManager);
        rockManager.addManager(paperManager);
        scissorsManager.addManager(rockManager);
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


    public Pair<Position, ViewElement> getState(Position position) {
        Pair<Position, ViewElement> result = new Pair<>(position, new ViewElement());

        for(GameElement element: getGameElements()){
            ModelElement e = element.getState(position);
            if(e instanceof RPSModelElement) result = (new Pair<>(position, new ViewElement(e.getValue(), e.getTag())));
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

    private RPSObstacle getConqueredObstacle(Position position) {
        RPSObstacle rock = (RPSObstacle) rockManager.getObstacleAtPosition(position);
        RPSObstacle paper = (RPSObstacle) paperManager.getObstacleAtPosition(position);
        RPSObstacle scissors = (RPSObstacle) scissorsManager.getObstacleAtPosition(position);

        if (rock != null && rock.canConquer(paperManager.getObstacleAtPosition(position))) {
            return rock;
        } else if (paper != null && paper.canConquer(scissorsManager.getObstacleAtPosition(position))) {
            return paper;
        } else if (scissors != null && scissors.canConquer(rockManager.getObstacleAtPosition(position))) {
            return scissors;
        }

        return null;
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
