package model;

import util.Position;

import java.util.List;
import java.util.Set;

public interface GameElement {
    List<Position> neighbors(Position position);
    List<Position> update();
}
