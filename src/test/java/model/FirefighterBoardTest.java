package model;

import model.elements.ModelElement;
import org.junit.jupiter.api.Test;
import util.Position;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FirefighterBoardTest {
  @Test
  void testColumnCount(){
    Board<List<ModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5);
    assertThat(board.columnCount()).isEqualTo(20);
  }
  @Test
  void testRowCount(){
    Board<List<ModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5);
    assertThat(board.rowCount()).isEqualTo(10);
  }
  @Test
  void testStepNumber(){
    Board<List<ModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5);
    for(int index = 0; index < 10; index++){
      assertThat(board.stepNumber()).isEqualTo(index);
      board.updateToNextGeneration();
    }
    assertThat(board.stepNumber()).isEqualTo(10);
  }
  @Test
  void testGetState_afterSet(){
    Board<List<ModelElement>> board = new FirefighterBoard(20, 10, 0, 0, 5);
    Position position = new Position(1,2);
    assertThat(board.getState(position)).isEmpty();
    board.setState(List.of(ModelElement.FIRE), position);
    assertThat(board.getState(position)).containsExactly(ModelElement.FIRE);
  }

  @Test
    void testGetState_afterUpdate(){
      Board<List<ModelElement>> board = new FirefighterBoard(8, 8, 0, 0, 5);
      Position p1 = new Position(1,2);
      Position p2 = new Position(5,2);
      board.setState(List.of(ModelElement.FIRE), p1);
      board.setState(List.of(ModelElement.FIREFIGHTER), p2);
      board.updateToNextGeneration();
      assertThat(board.getState(p1)).containsExactly(ModelElement.FIRE);
      assertThat(board.getState(new Position(4,2))).containsExactly(ModelElement.FIREFIGHTER);
      board.updateToNextGeneration();
      assertThat(board.getState(new Position(3,2))).containsExactly(ModelElement.FIRE);
  }

    @Test
    void testFireFightervsFire(){
      FirefighterBoard board = new FirefighterBoard(8, 8, 0, 0, 5);
      Position p1 = new Position(1,2);
      Position p2 = new Position(3,2);
      board.setState(List.of(ModelElement.FIRE), p1);
      board.setState(List.of(ModelElement.FIREFIGHTER), p2);
      board.updateToNextGeneration();
      board.updateToNextGeneration();
      assertThat(board.getFirePositions().isEmpty());


    }

}
