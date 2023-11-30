package view;

import javafx.scene.paint.Color;

public record ViewElement(Color color, String tag) {
    public ViewElement(){
        this(Color.WHITE, "EMPTY");
    }
    public Color getColor(){
        return color;
    }
    public String getTag(){
        return tag;
    }
}
