package view;

import javafx.scene.paint.Color;

public record ViewElement(Color color) {
    public ViewElement(){
        this(Color.WHITE);
    }
    public Color getColor(){
        return color;
    }
}
