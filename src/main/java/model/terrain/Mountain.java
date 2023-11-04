package model.terrain;

public class Mountain extends Terrain {

    public Mountain (int rowCount, int columnCount) {
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



