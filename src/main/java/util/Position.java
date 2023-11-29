package util;

public record Position(int row, int column) {
    public static Position randomPosition(int rowCount, int columnCount) {
        return new Position((int) (Math.random() * rowCount), (int) (Math.random() * columnCount));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return row == position.row && column == position.column;
    }
}

