package model.terrain;

import java.util.Random;

import java.util.Random;

public abstract class Terrain {
    private ModelTerrain[][] terrainGrid;
    private int rowCount;
    private int columnCount;

    public Terrain(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        terrainGrid = new ModelTerrain[rowCount][columnCount];
        initializeTerrain();
    }

    public abstract boolean isFirefighterAccessible();

    public abstract boolean fireCanSpread();

    private void initializeTerrain() {
        Random random = new Random();

        int totalCells = rowCount * columnCount;
        int numRoadMountainCells = (int) (0.4 * totalCells);

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                terrainGrid[row][col] = ModelTerrain.FOREST;
            }
        }

        // Create a connected road/mountain region
        int startX = random.nextInt(rowCount);
        int startY = random.nextInt(columnCount);
        createConnectedRegion(startX, startY, numRoadMountainCells, ModelTerrain.ROAD, random);
    }

    private void createConnectedRegion(int row, int col, int remainingCells, ModelTerrain terrainType, Random random) {
        if (remainingCells <= 0 || row < 0 || row >= rowCount || col < 0 || col >= columnCount) {
            return;
        }

        // Set the current cell to the specified terrain type
        terrainGrid[row][col] = terrainType;
        remainingCells--;

        // Shuffle the directions for a random order
        int[] directions = {1, 2, 3, 4};
        for (int i = 0; i < directions.length; i++) {
            int randomIndex = random.nextInt(4);
            int temp = directions[i];
            directions[i] = directions[randomIndex];
            directions[randomIndex] = temp;
        }

        // Recursively create the connected region in a random order
        for (int direction : directions) {
            int newRow = row;
            int newCol = col;
            if (direction == 1) {
                newRow = row - 1;
            } else if (direction == 2) {
                newRow = row + 1;
            } else if (direction == 3) {
                newCol = col - 1;
            } else {
                newCol = col + 1;
            }

            createConnectedRegion(newRow, newCol, remainingCells, terrainType, random);
        }
    }
}
