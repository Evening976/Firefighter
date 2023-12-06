package model.plague;

import general.model.GameElement;
import general.model.entities.ModelElement;
import javafx.util.Pair;
import model.Board;
import model.firefighterelements.PlagueModelE;
import util.Position;
import view.ViewElement;

import java.util.ArrayList;
import java.util.List;

public class PlagueBoard implements Board<List<PlagueModelE>> {
    public static final int INITIAL_CIVIL_COUNT = 40;
    public static final int INITIAL_DOCTOR_COUNT = 3;
    public static final int INITIAL_PLAGUED_COUNT = 3;
    private final int columnCount;
    private final int rowCount;
    private int step = 0;
    private PlagueManager plagueManager;
    private PopulationManager populationManager;
    public DoctorManager doctorManager;
    public PlagueBoard(int columnCount, int rowCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;

        initializeElements();
    }

    public void initializeElements() {
        doctorManager = new DoctorManager(rowCount, columnCount, INITIAL_DOCTOR_COUNT);
        plagueManager = new PlagueManager(rowCount, columnCount, INITIAL_PLAGUED_COUNT);
        populationManager = new PopulationManager(rowCount, columnCount, INITIAL_CIVIL_COUNT, INITIAL_PLAGUED_COUNT);
    }


    List<GameElement> getGameElements(){
        List<GameElement> gameElements = new ArrayList<>();
        gameElements.add(doctorManager);
        gameElements.add(plagueManager);
        gameElements.add(populationManager);
        return gameElements;
    }
    @Override
    public Pair<Position, ViewElement> getState(Position position) {
        Pair<Position, ViewElement> result = new Pair<>(position, new ViewElement());

        for(GameElement element: getGameElements()){
            ModelElement e = element.getState(position);
            if(e instanceof PlagueModelE) result = (new Pair<>(position, new ViewElement(e.getValue(), e.getTag())));
        }

        return result;
    }

    @Override
    public void setState(List<PlagueModelE> state, Position position) {
        for(GameElement element: getGameElements()){
            element.setState(state, position);
        }
    }

    @Override
    public int rowCount() {
        return rowCount;
    }

    @Override
    public int columnCount() {
        return columnCount;
    }

    @Override
    public List<Position> updateToNextGeneration() {
        List<Position> result = plagueManager.update(populationManager);
        result.addAll(populationManager.update(step));
        result.addAll(doctorManager.update(populationManager));

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
}
