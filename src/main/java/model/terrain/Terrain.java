package model.terrain;

import java.util.Random;

public class Terrain {
    private ModelTerrain[][] terrainGrid;
    private final int rowCount;
    private final int columnCount;

    public Terrain(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        terrainGrid = new ModelTerrain[rowCount][columnCount];
        initializeTerrain();
    }

    public boolean isFirefighterAccessible(int row, int col) {
        if (terrainGrid[row][col] == ModelTerrain.MOUNTAIN) {
            return false;
        }
        return true;
    }

    public boolean fireCanSpread(int row, int col) {
        if (terrainGrid[row][col] == ModelTerrain.FOREST) {
            return true;
        }
        return false;
    }

    public void setTerrainType(int row, int col, ModelTerrain terrainType) {
        terrainGrid[row][col] = terrainType;
    }

    public ModelTerrain getTerrainType(int row, int col) {
        return terrainGrid[row][col];
    }

    private void initializeTerrain() {
        Random random = new Random();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                terrainGrid[row][col] = ModelTerrain.FOREST;
            }
        }

        int totalCells = rowCount * columnCount;
        int numMountainCells = (int) (0.4 * totalCells);
        int numRoadCells = (int) (0.3 * totalCells);
        System.out.println("numMountainCells: " + numMountainCells);
        System.out.println("numRoadCells: " + numRoadCells);


        generateType(rowCount, columnCount, numMountainCells, ModelTerrain.MOUNTAIN, random);
        generateType(rowCount, columnCount, numRoadCells, ModelTerrain.ROAD, random);
    }

    public void generateType(int rowCount, int columnCount, int numCell, ModelTerrain terrainType, Random random) {
        int startX = random.nextInt(rowCount);
        int startY = random.nextInt(columnCount);
        createConnectedRegion(startX, startY, numCell, terrainType, random);
    }


    private void createConnectedRegion(int row, int col, int numCell, ModelTerrain terrainType, Random random) {
        if (numCell <= 0 || row < 0 || row >= rowCount || col < 0 || col >= columnCount) {
            System.out.println("error");
        }

        while (numCell > 0 ){
            terrainGrid[row][col] = terrainType;
            numCell--;
            System.out.println("numCell: " + numCell);
            int[] nextCell = randomDirectionGenerator(row, col, random);
            row = nextCell[0];
            col = nextCell[1];
        }
    }

    public int[] randomDirectionGenerator(int row, int col, Random random){
        int randomDirection = random.nextInt(4);
        switch (randomDirection){
            case 0:
                if(row - 1 >= 0){
                    return new int[]{row - 1, col};
                }
            case 1:
                if(row + 1 < rowCount){
                    return new int[]{row + 1, col};
                }
            case 2:
                if(col - 1 >= 0){
                    return new int[]{row, col - 1};
                }
            case 3:
                if(col + 1 < columnCount){
                    return new int[]{row, col + 1};
                }
        }
        int[] result = new int[]{row, col};
        return result;
    }

    public String toString(){
        String result = "";
        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < columnCount; col++){
                result += "[";
                switch (terrainGrid[row][col]){
                    case FOREST:
                        result += "F";
                        break;
                    case MOUNTAIN:
                        result += "M";
                        break;
                    case ROAD:
                        result += "R";
                        break;
                }
                result += "]";
            }
            result += "\n";
        }
        return result;
    }


}
