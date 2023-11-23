package util;

public class RandomGenerator {
    public static int randomInt(int min,int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static int randomInt(int max){
        return randomInt(0,max);
    }

    public static Position randomPosition(int rowCount, int columnCount) {
        return new Position(randomInt(rowCount - 1), randomInt(columnCount - 1));
    }
}
