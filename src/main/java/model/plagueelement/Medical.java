package model.plagueelement;

import model.firefighterelements.Mountain;
import model.firefighterelements.Road;
import util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static util.RandomGenerator.randomPosition;

public abstract class Medical extends DBoardElement {

    private List<Position> medPos;
    private final Set<Position> plaguePositions;

        public Medical(List<Position> doctorPositions, Set<Position> plaguePositions, int rowCount, int columnCount) {
            super(rowCount, columnCount);
            this.medPos = doctorPositions;
            this.plaguePositions = plaguePositions;
        }

        public Medical(Set<Position> plaguePositions, int initialCount, int rowCount, int columnCount){
            super(rowCount, columnCount);
            this.plaguePositions = plaguePositions;
            initializeElements(initialCount);
        }

        public List<Position> update(Set<Position> plaguePositions, Road road, Mountain mountain) {
            List<Position> result = new ArrayList<>();
            List<Position> doctorNewPositions = new ArrayList<>();
            for (Position doctorPosition : medPos) {
                Position newMedPos = neighborClosestToFire(doctorPosition, plaguePositions, road, mountain);
                doctorNewPositions.add(newMedPos);
                extinguish(newMedPos);
                result.add(doctorPosition);
                result.add(newMedPos);
                List<Position> neighborFirePositions = neighbors(newMedPos).stream()
                        .filter(plaguePositions::contains)
                        .toList();
                for (Position firePosition : neighborFirePositions)
                    extinguish(firePosition);
                result.addAll(neighborFirePositions);
            }
            medPos = doctorNewPositions;
            return result;
        }

        private void extinguish(Position position) {
            medPos.remove(position);
        }

        public abstract Position neighborClosestToFire(Position position, Set<Position> firePositions, Road road, Mountain mountain);

        @Override
        public void initializeElements(int initialCount) {
            medPos = new ArrayList<>();
            for(int index = 0; index < initialCount; index++)
                medPos.add(randomPosition(rowCount, columnCount));
        }


        public abstract List<DModelElement> getState(Position position);
        public abstract void setState(List<DModelElement> state, Position position);

        @Override
        public List<Position> getPositions() {
            return medPos;
        }

}
