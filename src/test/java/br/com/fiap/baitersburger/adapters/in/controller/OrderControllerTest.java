package br.com.fiap.baitersburger.adapters.in.controller;

import br.com.fiap.baitersburger.adapters.in.controller.dto.order.OrderRequestDTO;
import br.com.fiap.baitersburger.adapters.in.controller.dto.order.OrderResponseDTO;
import br.com.fiap.baitersburger.adapters.in.controller.mapper.OrderMapper;
import br.com.fiap.baitersburger.core.application.ports.in.order.FindOrderByStatusInputPort;
import br.com.fiap.baitersburger.core.application.ports.in.order.InsertOrderInputPort;
import br.com.fiap.baitersburger.core.application.ports.in.order.UpdateOrderStatusInputPort;
import br.com.fiap.baitersburger.core.domain.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import br.com.fiap.baitersburger.core.domain.model.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private InsertOrderInputPort insertOrderInputPort;

    @Mock
    private FindOrderByStatusInputPort findOrderByStatusInputPort;

    @Mock
    private  UpdateOrderStatusInputPort updateOrderStatusInputPort;


    private  OrderMapper orderMapper;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.orderMapper = Mappers.getMapper(OrderMapper.class);
        orderController = new OrderController(insertOrderInputPort, findOrderByStatusInputPort, updateOrderStatusInputPort, orderMapper);
    }

    @Test
    void insert() {
        OrderRequestDTO dto = new OrderRequestDTO();
        var order = orderController.insert(dto);

        assertNotNull(order);
        assertEquals(HttpStatus.CREATED,order.getStatusCode());

    }

    @Test
    void findByStatus() {
        var order = new Order();
        order.setStatus(OrderStatus.RECEIVED);
        order.setId("1");

        when(findOrderByStatusInputPort.findByStatus(OrderStatus.RECEIVED))
                .thenReturn(List.of(order));

        var response = orderController.findByStatus(OrderStatus.RECEIVED);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void updateOrderStatus() {

        String orderId = "1";
        OrderStatus status = OrderStatus.PREPARING;

        var response = orderController.updateOrderStatus(orderId, status);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(updateOrderStatusInputPort).updateOrderStatus(orderId, status);
    }
}