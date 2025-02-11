package com.order.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.order.order.dto.OrderDto;
import com.order.order.dto.OrderItemDto;
import com.order.order.model.Order;
import com.order.order.repository.OrderRepository;
import com.order.order.service.OrderService;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@ExtendWith(SpringExtension.class)
class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private OrderDto testOrderDto;

    @BeforeEach
    void setup() {
        // Criando um pedido de teste
        testOrderDto = new OrderDto();
        testOrderDto.setOrderNumber("12345");
        testOrderDto.setItens(List.of(
        		OrderItemDto.builder()
        		.productName("Produto A")
        		.quantity(2)
        		.price(new BigDecimal("10.00"))
        		.build(),
        		OrderItemDto.builder()
        		.productName("Produto B")
        		.quantity(1)
        		.price(new BigDecimal("20.00"))
        		.build()        		
        ));
    }

    @Test
    void testProcessOrder_ShouldSaveOrder_WhenOrderIsNew() {
        OrderDto savedOrder = orderService.processOrder(testOrderDto);

        assertNotNull(savedOrder);
        assertEquals("12345", savedOrder.getOrderNumber());
        assertEquals(2, savedOrder.getItens().size());

        Optional<Order> orderFromDb = orderRepository.findByOrderNumber("12345");
        assertTrue(orderFromDb.isPresent());
        assertEquals(2, orderFromDb.get().getItens().size());
    }

    @Test
    void testProcessOrder_ShouldThrowException_WhenOrderAlreadyExists() {
        // Primeiro, salvar um pedido
        orderService.processOrder(testOrderDto);

        // Depois, tentar salvar o mesmo pedido novamente (deve lançar erro)
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.processOrder(testOrderDto);
        });

        assertEquals("Pedido já existe!", exception.getMessage());
    }

    @Test
    void testGetOrderById_ShouldReturnOrder_WhenOrderExists() {
        OrderDto savedOrder = orderService.processOrder(testOrderDto);

        Optional<OrderDto> retrievedOrder = orderService.getOrderById(savedOrder.getId());

        assertTrue(retrievedOrder.isPresent());
        assertEquals("12345", retrievedOrder.get().getOrderNumber());
    }

    @Test
    void testGetOrderById_ShouldReturnEmpty_WhenOrderDoesNotExist() {
        Optional<OrderDto> retrievedOrder = orderService.getOrderById(999L);
        assertTrue(retrievedOrder.isEmpty());
    }
}

