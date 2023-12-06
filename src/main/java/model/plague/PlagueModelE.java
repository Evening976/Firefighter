package model.firefighterelements;

import general.model.entities.ModelElement;
import javafx.scene.paint.Color;


public class PlagueModelE implements ModelElement {
    private final Color color;
    private final String tag;
    public PlagueModelE(Color color, String tag){
        this.color = color;
        this.tag = tag;
    }
    @Override
    public Color getValue(){
        return color;
    }

    @Override
    public String getTag() {
        return tag;
    }
}
