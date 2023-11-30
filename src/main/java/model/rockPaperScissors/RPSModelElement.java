package model.rockPaperScissors;

import general.model.entities.ModelElement;
import javafx.scene.paint.Color;

public class RPSModelElement implements ModelElement {
/*        ROCK(Color.BLACK),
        PAPER(Color.YELLOW),
        SCISSORS(Color.BLUE),
        EMPTY(Color.WHITE);*/

        private final Color color;
        private final String tag;

        RPSModelElement(Color color, String tag){
                this.color = color;
                this.tag = tag;
        }

        @Override
        public Color getValue() {
                return color;
        }

        @Override
        public String getTag() {
                return tag;
        }

}
