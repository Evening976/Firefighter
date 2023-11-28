package model.firefighterelements;

import general.model.entity.ModelElement;
import javafx.scene.paint.Color;


public enum FFModelElement implements ModelElement {
  CLOUD(Color.CYAN), FIREFIGHTERPERSON(Color.BLUE), FIRE(Color.RED),
  FIRETRUCK(Color.LIGHTSALMON), MOUNTAIN(Color.BLACK), ROAD(Color.GRAY),
  ROCK(Color.PURPLE), EMPTY(Color.WHITE);

private final Color color;
FFModelElement(Color color){
  this.color = color;
}
@Override
public Color getValue(){
  return color;
}
}

