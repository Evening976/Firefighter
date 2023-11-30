package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import util.Position;

import java.util.List;

public class Painter extends Canvas implements Grid<ViewElement>{

    private void paintElementAtPosition(ViewElement element, Position position) {
        paintSquare(position.row(), position.column(), element.getColor());
    }
    private int squareWidth;
    private int squareHeight;
    private int columnCount;
    private int rowCount;

    @Override
    public void repaint(List<Pair<Position, ViewElement>> positionedElements) {
        clear(positionedElements);
        paint(positionedElements);
        paintLines();
    }

    private void clear(List<Pair<Position, ViewElement>> positionedElements) {
        for (Pair<Position, ViewElement> positionElement : positionedElements) {
            Position position = positionElement.getKey();
            clearSquare(position.row(), position.column());
        }
    }

    private void paint(List<Pair<Position, ViewElement>> positionedElements) {
        for(Pair<Position, ViewElement> pair : positionedElements){
            paintElementAtPosition(pair.getValue(), pair.getKey());
        }
    }

    @Override
    public void repaint(ViewElement[][] elements) {
        clear();
        paint(elements);
        paintLines();
    }

    private void clear() {
        getGraphicsContext2D().clearRect(0,0,getWidth(), getHeight());
    }

    private void paint(ViewElement[][] elements) {
        for(int column = 0; column < columnCount; column++)
            for(int row = 0; row < rowCount; row++){
                paintElementAtPosition(elements[row][column], new Position(row, column));
            }
    }

    public int columnCount() {
        return columnCount;
    }

    public int rowCount() {
        return rowCount;
    }

    @Override
    public void setDimensions(int columnCount, int rowCount, int squareWidth, int squareHeight) {
        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }

    private void paintLines(){
        paintHorizontalLines();
        paintVerticalLines();
    }

    private void paintVerticalLines() {
        for(int column = 0; column < columnCount; column++)
            getGraphicsContext2D().strokeLine(column*squareWidth, 0,column*squareWidth, getHeight());
    }

    private void paintHorizontalLines() {
        for(int row = 0; row < rowCount; row++)
            getGraphicsContext2D().strokeLine(0, row*squareHeight, getWidth(), row*squareHeight);
    }

    private void paintSquare(int row, int column, Color color){
        getGraphicsContext2D().setFill(color);
        getGraphicsContext2D().fillRect(row*squareHeight,column*squareWidth,squareHeight,squareWidth);
    }

    private void clearSquare(int row, int column){
        getGraphicsContext2D().clearRect(row*squareHeight,column*squareWidth,squareHeight,squareWidth);
    }
}