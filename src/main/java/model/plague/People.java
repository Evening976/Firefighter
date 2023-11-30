package model.plague;

import general.model.entities.Entity;
import util.Position;

public class People extends Entity {
    boolean infected = false;
    boolean alive = true;
    int daysInfected = 0;
    public People(Position position) {
        super(position);
    }

    public People(Position position, boolean infected) {
        super(position);
        this.infected = infected;
    }

    public boolean isInfected(){
        return infected;
    }

    public void setInfected(boolean infected){
        this.infected = infected;
    }

    public void updateStep(){
        if(infected){
            daysInfected++;
            if(daysInfected >= 30){
                alive = false;
            }
        }
    }

    public boolean isAlive(){
        return alive;
    }
}
