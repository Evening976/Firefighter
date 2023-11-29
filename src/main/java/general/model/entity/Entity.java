package general.model.entity;

import util.Position;

public abstract class Entity {
    Position position;

    public Entity(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;

        Entity entity = (Entity) obj;
        return position != null ? position.equals(entity.position) : entity.position == null;
    }
}
