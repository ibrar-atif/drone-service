package de.su.droneservice.drone.integration.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.su.droneservice.DroneServiceApplication;
import de.su.droneservice.drone.model.Direction;
import de.su.droneservice.drone.model.Drone;
import de.su.droneservice.drone.model.PatchTurn;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("integration")
@SpringBootTest(classes = { DroneServiceApplication.class })
@AutoConfigureMockMvc
public class DroneControllerIntegrationTest {

    private static final String CREATE_DRONE = "/drone/";

    private static final String TURN_RIGHT = "/drone/turn/right";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateDrone() throws Exception {
        this.mockMvc.perform(post(CREATE_DRONE)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new Drone(null,Direction.NORTH,null)))
                        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.notNullValue()));
    }

    @Test
    public void shouldTurnToTheSOUTHWhenAskedToMoveRigthFacingEast() throws Exception {

        String result = this.mockMvc.perform(post(CREATE_DRONE)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new Drone(null,Direction.NORTH,null)))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Drone drone = objectMapper.readValue(result, Drone.class);
        this.mockMvc.perform(patch("http://localhost:54053/drone/"+drone.getId()+"/turn")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(PatchTurn.builder().op("change").turn("right").build()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facingTo",is("EAST")));

    }

    @Test
    public void shouldMoveToTheEast() throws Exception {

        String result = this.mockMvc.perform(post(CREATE_DRONE)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(new Drone(null,Direction.EAST,null)))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Drone drone = objectMapper.readValue(result, Drone.class);
        this.mockMvc.perform(patch("http://localhost:54053/drone/"+drone.getId()+"/move/1")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(PatchTurn.builder().op("change").turn("right").build()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cordinates",is(List.of(1,0))));

    }

    public static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
