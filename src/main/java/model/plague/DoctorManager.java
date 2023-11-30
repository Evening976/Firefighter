package model.plague;

import general.model.entities.EntityManager;
import general.model.entities.ModelElement;
import general.model.obstacles.ObstacleManager;
import javafx.scene.paint.Color;
import model.firefighterelements.PlagueModelE;
import util.Position;

import java.util.*;

public class DoctorManager extends EntityManager {

    List<ObstacleManager> obstacleManagers;
    Set<Doctor> doctors;

    public DoctorManager(int rowCount, int columnCount, int initialCount, ObstacleManager... obstacleManagers){
        super(rowCount, columnCount, initialCount);

        this.tag = new PlagueModelE(Color.CYAN, "[D]");
        doctors = new HashSet<>();

        if(obstacleManagers.length == 0) this.obstacleManagers = Arrays.asList(new ObstacleManager[0]);
        else this.obstacleManagers = Arrays.asList(obstacleManagers);
        initializeElements();
    }

    public List<Position> update(PopulationManager populationManager){
        List<Position> result = new ArrayList<>();
        Set<Doctor> doctorNewPositions = new HashSet<>();
        for(Position doctorPosition: getPositions()){
            Position newDoctorPosition = neighborClosestToVirusedDude(doctorPosition, populationManager);
            doctorNewPositions.add(new Doctor(newDoctorPosition));
            populationManager.cure(newDoctorPosition);
            result.add(doctorPosition);
            result.add(newDoctorPosition);
            List<Position> neighborCivilPositions = neighbors(newDoctorPosition);
            for(Position guyPosition: neighborCivilPositions){
                populationManager.cure(guyPosition);
            }
            result.addAll(neighborCivilPositions);

        }
        doctors.clear();
        doctors.addAll(doctorNewPositions);

        return result;
    }

    protected Position neighborClosestToVirusedDude(Position position, PopulationManager populationManager) {
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(neighbors(position));

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (populationManager.getPositions().contains(current) && populationManager.isInfected(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors(current)) {
                if (seen.contains(adjacent) || !obstacleManagers.stream().allMatch(obstacleManager -> obstacleManager.accept(current))) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    @Override
    public Collection<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        for (Doctor doctor : doctors) {
            positions.add(doctor.getPosition());
        }
        return positions;
    }

    @Override
    public void initializeElements() {
        for (int index = 0; index < initialCount; index++) {
            doctors.add(new Doctor(Position.randomPosition(rowCount, columnCount)));
        }
    }

    @Override
    public void setState(List<? extends ModelElement> state, Position position) {
        doctors.removeIf(doctor -> doctor.getPosition().equals(position));
        for(ModelElement element: state){
            if(element.equals(tag)){
                doctors.add(new Doctor(position));
            }
        }
    }


}
