package model;

import util.Position;

import java.util.List;

public interface GameElement {
    List<Position> neighbors(Position position);
    List<Position> update();
}
