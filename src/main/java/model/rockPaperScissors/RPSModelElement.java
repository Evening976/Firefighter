package model.rockPaperScissors;

import general.model.entity.ModelElement;
import javafx.scene.paint.Color;

public enum RPSModelElement implements ModelElement {
        ROCK(Color.BLACK),
        PAPER(Color.BEIGE),
        SCISSORS(Color.BLUE),
        EMPTY(Color.WHITE);

        private final Color color;

        RPSModelElement(Color color){
                this.color = color;
        }

        @Override
        public Color getValue() {
                return null;
        }

}
