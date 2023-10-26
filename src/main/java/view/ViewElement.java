package view;

import javafx.scene.paint.Color;

public enum ViewElement {
  FIREFIGHTER(Color.BLUE), FIRE(Color.RED), CLOUD(Color.AQUAMARINE), EMPTY(Color.WHITE);
  final Color color;
  ViewElement(Color color) {
    this.color = color;
  }
}
