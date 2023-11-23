package model;

import model.firefighterelements.FFModelElement;
import org.junit.jupiter.api.Test;
import util.Position;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class FirefighterBoardTest {
  @Test
  void testColumnCount(){
    Board<List<FFModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5,0);
    assertThat(board.columnCount()).isEqualTo(20);
  }
  @Test
  void testRowCount(){
    Board<List<FFModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5,0);
    assertThat(board.rowCount()).isEqualTo(10);
  }
  @Test
  void testStepNumber(){
    Board<List<FFModelElement>> board = new FirefighterBoard(20, 10, 1, 3, 5,0);
    for(int index = 0; index < 10; index++){
      assertThat(board.stepNumber()).isEqualTo(index);
      board.updateToNextGeneration();
    }
    assertThat(board.stepNumber()).isEqualTo(10);
  }
  @Test
  void testGetState_afterSet(){
    Board<List<FFModelElement>> board = new FirefighterBoard(20, 10, 0, 0, 5,0);
    Position position = new Position(1,2);
    assertThat(board.getState(position)).isEmpty();
    board.setState(List.of(FFModelElement.FIRE), position);
    assertThat(board.getState(position)).containsExactly(FFModelElement.FIRE);
  }

  @Test
    void testGetState_afterUpdate(){
      Board<List<FFModelElement>> board = new FirefighterBoard(8, 8, 0, 0, 5,0);
      Position p1 = new Position(1,2);
      Position p2 = new Position(5,2);
      board.setState(List.of(FFModelElement.FIRE), p1);
      board.setState(List.of(FFModelElement.FIREFIGHTERPERSON), p2);
      board.updateToNextGeneration();
      assertThat(board.getState(p1)).containsExactly(FFModelElement.FIRE);
      assertThat(board.getState(new Position(4,2))).containsExactly(FFModelElement.FIREFIGHTERPERSON);
      board.updateToNextGeneration();
      assertThat(board.getState(new Position(3,2))).containsExactly(FFModelElement.FIRE);
  }

    @Test
    void testFireFightervsFire(){
      FirefighterBoard board = new FirefighterBoard(8, 8, 0, 0, 5,0);
      Position p1 = new Position(1,2);
      Position p2 = new Position(3,2);
      board.setState(List.of(FFModelElement.FIRE), p1);
      board.setState(List.of(FFModelElement.FIREFIGHTERPERSON), p2);
      board.updateToNextGeneration();
      board.updateToNextGeneration();
      assertThat(board.getFirePositions().isEmpty());
    }

    @Test
    void testCloud(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 0, 0, 0,0);
        Position p1 = new Position(1,2);
        Position p2 = new Position(3,2);
        board.setState(List.of(FFModelElement.FIRE), p1);
        board.setState(List.of(FFModelElement.CLOUD ), p2);
        for (int i = 0; i < 5; i++) {
            board.updateToNextGeneration();
            board.printBoard();
            System.out.println("_______");
            if(i == 2){
              board.setState(List.of(FFModelElement.CLOUD), new Position(0,2));
            }
        }
    }

    @Test
    void testFireTruck(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 0, 0, 0, 0);
        Position p1 = new Position(1,2);
        Position p2 = new Position(4,2);
        board.setState(List.of(FFModelElement.FIRE), p1);
        board.setState(List.of(FFModelElement.FIRETRUCK), p2);
        for (int i = 0; i < 5; i++) {
            board.updateToNextGeneration();
            board.printBoard();
            System.out.println("_______");

        }
    }

    @Test
    void testFireTruckInit(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 0, 0, 0, 0);
        Position position = new Position(1,2);
        assertThat(board.getState(position)).isEmpty();
        board.setState(List.of(FFModelElement.FIRETRUCK), position);
        assertThat(board.getState(position)).containsExactly(FFModelElement.FIRETRUCK);
    }

    @Test
    void testFireSpread(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 0, 0, 0, 0);
        board.printBoard();
        board.setState(List.of(FFModelElement.MOUNTAIN), new Position(1,1));
        board.setState(List.of(FFModelElement.FIRE), new Position(1,2));
        board.printBoard();
        board.updateToNextGeneration();
        board.printBoard();
    }

    @Test
    void testFireFighterSpread(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 0, 0, 0, 0);
        board.clearBoard();
        board.setState(List.of(FFModelElement.FIRE), new Position(0,2));
        board.setState(List.of(FFModelElement.FIRE), new Position(3,4));

        board.setState(List.of(FFModelElement.FIREFIGHTERPERSON), new Position(3,2));
        board.setState(List.of(FFModelElement.MOUNTAIN), new Position(1,2));
        for (int i =0 ; i < 5; i++) {
            board.updateToNextGeneration();
            board.printBoard();
            System.out.println("_______");
        }
    }

    @Test
    void testFireSpreadRock(){
        FirefighterBoard board = new FirefighterBoard(5, 5, 2, 1, 0, 0);
        board.setState(List.of(FFModelElement.FIRE), new Position(3,3));
        board.setState(List.of(FFModelElement.ROCK), new Position(3,2));
        for (int i =0 ; i < 5; i++) {
            board.updateToNextGeneration();
            board.printBoard();
            System.out.println("_______");
        }
    }

    @Test
    void mathTest(){
        System.out.println(0%4);
    }
}
