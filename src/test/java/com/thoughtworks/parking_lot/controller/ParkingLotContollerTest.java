package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
class ParkingLotControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingLotService parkingLotService;

    @Test
    void should_save_parking_lot() throws Exception {
        ParkingLot parkingLot = dummyParkingLot();

        when(parkingLotService.save(any())).thenReturn(parkingLot);

        ResultActions result = mvc.perform(post("/parkingLot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));

        result.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void should_delete_parking_lot() throws Exception {
        when(parkingLotService.findByNameContaining("ParkingLot")).thenReturn(dummyParkingLot());

        ResultActions result = mvc.perform(delete("/parkingLot/ParkingLot"));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shoud_get_parkingLot_list() throws Exception {
        when(parkingLotService.findAll(new PageRequest(0, 15)))
                .thenReturn(Collections.singleton(new ParkingLot()));

        ResultActions result = mvc.perform(get("/parkingLot?page=0&pagesize=15"));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_get_parkingLot() throws Exception {
        when(parkingLotService.findByNameContaining("ParkingLot")).thenReturn(dummyParkingLot());

        ResultActions result = mvc.perform(get("/parkingLot/ParkingLot"));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_patch_parkingLot() throws Exception {
        when(parkingLotService.findByNameContaining("ParkingLot")).thenReturn(dummyParkingLot());

        ResultActions result = mvc.perform(patch("/parkingLot/ParkingLot?capacity=5", dummyParkingLot())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyParkingLot())));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    private ParkingLot dummyParkingLot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName("ParkingLot");
        parkingLot.setCapacity(10);
        parkingLot.setLocation("Sample Location");

        return parkingLot;
    }

}