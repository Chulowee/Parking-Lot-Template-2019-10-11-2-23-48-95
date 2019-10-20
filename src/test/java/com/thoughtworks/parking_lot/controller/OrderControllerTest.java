package com.thoughtworks.parking_lot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.model.Order;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.OrderService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@ActiveProfiles(profiles = "test")
class OrderControllerTest {
    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_save_order_by_name() throws Exception {
        Order order = dummyOrder();

        when(orderService.createOrder(any())).thenReturn(order);

        ResultActions result = mvc.perform(post("/parkingLot/Sample/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)));

        result.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void should_patch_parkingLot() throws Exception{
        when(orderService.findByNameContaining("UHHG123")).thenReturn(Optional.of(dummyOrder()));

        ResultActions result = mvc.perform(patch("/parkingLot/Sample/order/UHHG123", dummyOrder())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyOrder())));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    private Order dummyOrder() {
        Order order = new Order();
        order.setOrderNumber(1);
        order.setPlateNumber("ABC123");
        order.setCreationTime("2000-01-01");
        order.setCloseTime("2000-01-02");
        order.setOrderStatus("Open");

        return order;
    }
}