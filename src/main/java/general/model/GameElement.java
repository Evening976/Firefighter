package general.model;

import general.model.entities.ModelElement;
import util.Position;

import java.util.Collection;
import java.util.List;

public interface GameElement {
    Collection<Position> getPositions();
    ModelElement getState(Position position);
    void setState(List<? extends ModelElement> state, Position position);
    void initializeElements();
}
