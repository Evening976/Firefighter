package general.model.entity;

import javafx.scene.paint.Color;

public interface ModelElement {
    public ModelElement EMPTY = new ModelElement() {
        @Override
        public Color getValue() {
            return Color.WHITE;
        }
    };

    Color getValue();
}
