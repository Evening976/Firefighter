package util;

public record Position(int row, int column) {
    public static Position randomPosition(int rowCount, int columnCount) {
        return new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount));
    }
}

