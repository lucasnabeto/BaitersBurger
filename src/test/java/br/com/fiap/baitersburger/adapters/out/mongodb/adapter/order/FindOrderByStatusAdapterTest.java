package br.com.fiap.baitersburger.adapters.out.mongodb.adapter.order;

import br.com.fiap.baitersburger.adapters.out.mongodb.repository.OrderRepository;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.entity.OrderEntity;
import br.com.fiap.baitersburger.adapters.out.mongodb.repository.mappers.OrderEntityMapper;
import br.com.fiap.baitersburger.core.domain.enums.OrderStatus;
import br.com.fiap.baitersburger.core.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
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


    private OrderEntityMapper orderEntityMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderEntityMapper = Mappers.getMapper(OrderEntityMapper.class);
        adapter = new FindOrderByStatusAdapter(orderRepository,orderEntityMapper);
    }

    @Test
    void returnsOrdersWhenStatusExistsInRepositoryTest() {

        var order1 = new Order();
        order1.setId("1");
        order1.setStatus(OrderStatus.RECEIVED);

        var order2 = new Order();
        order2.setId("2");
        order2.setStatus(OrderStatus.RECEIVED);

        var orderEntity1 = orderEntityMapper.toOrderEntity(order1);
        var orderEntity2 = orderEntityMapper.toOrderEntity(order2);

        when(orderRepository.findByStatus(OrderStatus.RECEIVED)).thenReturn(List.of(orderEntity1, orderEntity2));

        var result = adapter.findByStatus(OrderStatus.RECEIVED);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(x -> x.getId().equals("1")));
        assertTrue(result.stream().anyMatch(x -> x.getId().equals("2")));
    }

    @Test
    void returnsEmptyListWhenStatusDoesNotExistInRepositoryTest() {
        when(orderRepository.findByStatus(OrderStatus.READY)).thenReturn(List.of());

        var result = adapter.findByStatus(OrderStatus.READY);

        assertTrue(result.isEmpty());
    }
}