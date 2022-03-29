package de.su.droneservice.drone.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {
    private String id;
    private Direction facingTo;
    private List<Integer> cordinates;


}
