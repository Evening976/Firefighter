package general.model.entity;

import javafx.scene.paint.Color;

public interface ModelElement {
    public ModelElement EMPTY = () -> Color.WHITE;

    Color getValue();
}
