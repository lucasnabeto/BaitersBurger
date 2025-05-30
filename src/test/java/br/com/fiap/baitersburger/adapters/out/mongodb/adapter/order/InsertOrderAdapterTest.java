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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InsertOrderAdapterTest {

    @InjectMocks
    private InsertOrderAdapter adapter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderEntityMapper orderEntityMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void insert() {
    }

    @Test
    void insertsOrderSuccessfullyWhenValidOrderProvidedTest() {
        var order = new Order();
        var orderEntity = new OrderEntity();
        when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);

        adapter.insert(order);

        verify(orderEntityMapper).toOrderEntity(order);
        verify(orderRepository).insert(orderEntity);
    }
}