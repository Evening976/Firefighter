package model;

import javafx.util.Pair;
import model.plagueelements.DModelElement;
import util.Position;
import view.ViewElement;

import java.util.List;

public class PlagueBoard implements Board<List<DModelElement>>{
    @Override
    public Pair<Position, ViewElement> getState(Position position) {
        return null;
    }

    @Override
    public void setState(List<DModelElement> state, Position position) {

    }

    @Override
    public int rowCount() {
        return 0;
    }

    @Override
    public int columnCount() {
        return 0;
    }

    @Override
    public List<Position> updateToNextGeneration() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public int stepNumber() {
        return 0;
    }

    public List<Position> neighbors(Position position) {
        return null;
    }
}
