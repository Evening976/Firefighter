package general.model.entities;

import util.Position;

import java.util.Objects;

public abstract class Entity {
    Position position;

    public Entity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity entity)) return false;
        return Objects.equals(position, entity.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
