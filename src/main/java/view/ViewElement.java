package view;

import javafx.scene.paint.Color;

public record ViewElement(Color color, String tag) {
    public ViewElement(){
        this(Color.WHITE, "[ ]");
    }
    public Color getColor(){
        return color;
    }
    public String getTag(){
        return tag;
    }
}
