package de.su.droneservice.drone.controller;

import de.su.droneservice.drone.DroneService;
import de.su.droneservice.drone.model.Drone;
import de.su.droneservice.drone.model.PatchTurn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drone")
public class DroneController {

    public DroneController(){
        System.out.println("created");
    }

    @Autowired
    private DroneService droneService;

    @PostMapping(value = {"/",""})
    public ResponseEntity<Drone> createDrone(@RequestBody Drone drone){
        return new ResponseEntity<>(droneService.create(drone), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/turn")
    public ResponseEntity<Drone> turn(@PathVariable("id") String id, @RequestBody PatchTurn patchTurn){

        return new ResponseEntity<>(droneService.turn(id, patchTurn.getTurn()),HttpStatus.OK);
    }

    @PatchMapping("/{id}/move/{steps}")
    public ResponseEntity<Drone> move(@PathVariable("id") String id, @PathVariable("steps") Integer steps){

        return new ResponseEntity<>(droneService.move(id, steps),HttpStatus.OK);
    }
}
