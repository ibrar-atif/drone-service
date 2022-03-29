package de.su.droneservice.drone.integration.service;

import de.su.droneservice.drone.DroneService;
import de.su.droneservice.drone.model.Direction;
import de.su.droneservice.drone.model.Drone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DroneServiceTest {

    private DroneService droneService;

    @BeforeEach
    public void init(){
        droneService = new DroneService();
    }
    @Test
    public void createDrone(){
        Drone drone = new Drone();
        drone.setFacingTo(Direction.NORTH);
        Drone result = droneService.create(drone);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getFacingTo()).isEqualTo(Direction.NORTH);
    }
}
