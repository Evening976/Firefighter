package view;

import javafx.scene.paint.Color;

public enum ViewElement {
  FIREFIGHTERPERSON(Color.BLUE), FIRETRUCK(Color.LIGHTSALMON),FIRE(Color.RED), CLOUD(Color.AQUAMARINE), EMPTY(Color.WHITE);
  final Color color;
  ViewElement(Color color) {
    this.color = color;
  }
}
