package br.com.fiap.baitersburger.adapters.out.mongodb.adapter.order;

import br.com.fiap.baitersburger.adapters.out.mongodb.repository.OrderRepository;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.entity.OrderEntity;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.mappers.OrderEntityMapper;
import br.com.fiap.baitersburger.core.domain.enums.OrderStatus;
import br.com.fiap.baitersburger.core.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindOrderByStatusAdapterTest {

    @InjectMocks
    private FindOrderByStatusAdapter adapter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntityMapper orderEntityMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnsOrdersWhenStatusExistsInRepositoryTest() {
        var orderEntity1 = new OrderEntity();
        var orderEntity2 = new OrderEntity();
        var order1 = new Order();
        var order2 = new Order();
        when(orderRepository.findByStatus(OrderStatus.RECEIVED)).thenReturn(List.of(orderEntity1, orderEntity2));
        when(orderEntityMapper.toOrder(orderEntity1)).thenReturn(order1);
        when(orderEntityMapper.toOrder(orderEntity2)).thenReturn(order2);

        var result = adapter.findByStatus(OrderStatus.RECEIVED);

        assertEquals(2, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
    }

    @Test
    void returnsEmptyListWhenStatusDoesNotExistInRepositoryTest() {
        when(orderRepository.findByStatus(OrderStatus.READY)).thenReturn(List.of());

        var result = adapter.findByStatus(OrderStatus.READY);

        assertTrue(result.isEmpty());
    }
}