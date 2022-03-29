package de.su.droneservice.drone;

import de.su.droneservice.drone.exception.BusinessException;
import de.su.droneservice.drone.model.Direction;
import de.su.droneservice.drone.model.Drone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DroneService {

    static Map<String, Drone> drones = new HashMap<>();

    @Value("${field.length}")
    private Integer length;

    @Value("${field.width}")
    private Integer width;


    public Drone create(Drone droneRequest){
        Drone drone = new Drone(UUID.randomUUID().toString()
                ,droneRequest.getFacingTo()!=null?droneRequest.getFacingTo():Direction.EAST
                ,List.of(0,0));
        drones.put(drone.getId(),drone);
        return drone;
    }

    public Drone turn(String id, String direction){
        Drone drone = drones.get(id);
        if(drone==null)
            new BusinessException("Invalid drone id");
        if(!direction.equals("left") && !direction.equals("right")){
            throw new BusinessException("Invalid movement");
        }
        if(direction.equals("left")){
            Direction changedDirection = drone.getFacingTo().getLeft();
            drone.setFacingTo(changedDirection);
        }
        else if(direction.equals("right")){
            Direction changedDirection = drone.getFacingTo().getRight();
            drone.setFacingTo(changedDirection);
        }
        else{
            throw new BusinessException("Unsupported move");
        }
        return drone;
    }

    public Drone move(String id, Integer steps) {
        Drone drone = drones.get(id);
        if(drone==null)
            new BusinessException("Invalid drone id");
        drone = move(drone, steps);
        drones.put(drone.getId(),drone);
        return drone;
    }

    private Drone move(Drone drone, Integer steps){
        Integer xcordinate = drone.getCordinates().get(0);
        Integer ycordinate = drone.getCordinates().get(1);
        xcordinate = xcordinate + steps*drone.getFacingTo().getXMovement();
        ycordinate = ycordinate + steps*drone.getFacingTo().getYMovement();

        if(xcordinate>=length || xcordinate<0 || ycordinate>=width || ycordinate<0){
            throw new BusinessException("movement not possible");
        }
        drone.setCordinates(List.of(xcordinate,ycordinate));
        return drone;
    }
}
