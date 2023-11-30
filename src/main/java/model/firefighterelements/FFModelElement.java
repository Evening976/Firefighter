package model.firefighterelements;

import general.model.entity.ModelElement;
import javafx.scene.paint.Color;


public class FFModelElement implements ModelElement {
private final Color color;
private final String tag;
public FFModelElement(Color color, String tag){
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

