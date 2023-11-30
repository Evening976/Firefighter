package general.model.entity;

import javafx.scene.paint.Color;

public interface ModelElement {
    ModelElement EMPTY = new ModelElement() {
        @Override
        public Color getValue() {
            return Color.WHITE;
        }

        @Override
        public String getTag() {
            return "EMPTY";
        }
    };

    Color getValue();
    String getTag();
}
