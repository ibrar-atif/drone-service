package de.su.droneservice.drone.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchTurn {

    private String op;
    private String turn;
}
