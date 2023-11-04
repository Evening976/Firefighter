package model.terrain;

public class Road extends Terrain {

    public Road(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    @Override
    public boolean isFirefighterAccessible() {
        return false;
    }

    @Override
    public boolean fireCanSpread() {
        return false;
    }
}

