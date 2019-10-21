package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.service.OrderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@ActiveProfiles(profiles = "test")
class ParkingOrderControllerTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_save_order_by_name() throws Exception {
        ParkingOrder parkingOrder = dummyOrder();

        when(orderService.createOrder(any(),any())).thenReturn(parkingOrder);

        ResultActions result = mvc.perform(post("/parkingLot/Sample/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingOrder)));

        result.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void should_patch_parkingLot() throws Exception{
        when(orderService.updateOrder("UHHG123")).thenReturn(dummyOrder());

        ResultActions result = mvc.perform(patch("/parkingLot/Sample/order/UHHG123", dummyOrder())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyOrder())));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_fail_creation() throws Exception {
        ParkingOrder parkingOrder = dummyOrder();

        when(orderService.createOrder(any(), any())).thenReturn(null);

        ResultActions result = mvc.perform(post("/parkingLot/Sample/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingOrder)));

        result.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.is("The parking lot is full!")));
    }
    private ParkingOrder dummyOrder() {
        ParkingOrder parkingOrder = new ParkingOrder();
        parkingOrder.setOrderNumber("1");
        parkingOrder.setPlateNumber("ABC123");
        parkingOrder.setCreationTime("2000-01-01");
        parkingOrder.setCloseTime("2000-01-02");
        parkingOrder.setOrderStatus("Open");

        return parkingOrder;
    }
}