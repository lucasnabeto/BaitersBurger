package br.com.fiap.baitersburger.adapters.out.mongodb.adapter.order;

import br.com.fiap.baitersburger.adapters.out.mongodb.repository.OrderRepository;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.entity.OrderEntity;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.mappers.OrderEntityMapper;
import br.com.fiap.baitersburger.core.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindOrderByIdAdapterTest {

    @InjectMocks
    private FindOrderByIdAdapter adapter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntityMapper orderEntityMapper;

    @Test
    void findOrderById() {
    }

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnsOrderWhenOrderIdExistsInRepositoryTest() {
        var orderEntity = new OrderEntity();
        var order = new Order();
        when(orderRepository.findById("order123")).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        var result = adapter.findOrderById("order123");

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void returnsEmptyOptionalWhenOrderIdDoesNotExistInRepositoryTest() {
        when(orderRepository.findById("nonexistentOrderId")).thenReturn(Optional.empty());

        var result = adapter.findOrderById("nonexistentOrderId");

        assertFalse(result.isPresent());
    }

}