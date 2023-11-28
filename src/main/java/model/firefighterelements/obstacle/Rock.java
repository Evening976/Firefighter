package model.firefighterelements.obstacle;

import general.model.entity.ModelElement;
import model.firefighterelements.Entity;
import model.firefighterelements.FFModelElement;
import util.Position;

import java.util.ArrayList;
import java.util.List;

public class Rock extends Entity {
    private final int fireResistanceSteps;
    private int stepsRemaining;

    public Rock(int rowCount, int columnCount) {
        super(rowCount, columnCount);

        this.positions = new ArrayList<>();
        tag = FFModelElement.ROCK;
        this.fireResistanceSteps = 4;

        initializeElements(getInitCount());
    }

    public int getInitCount() {
        return (int) (rowCount * 0.5);
    }

    @Override
    public void initializeElements(int initialCount) {
        super.initializeElements(initialCount);
        resetStepsRemaining();
    }


    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        super.setState(state, position);
        resetStepsRemaining();
    }

    public boolean isRock(Position position) {
        return getPositions().contains(position);
    }

    public boolean isFireResistant() {
        return stepsRemaining > 0;
    }

    public void decrementStepsRemaining() {
        stepsRemaining = Math.max(0, stepsRemaining - 1);
    }

    public void resetStepsRemaining() {
        stepsRemaining = fireResistanceSteps;
    }
}
