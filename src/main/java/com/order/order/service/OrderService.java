package com.order.order.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.order.order.dto.OrderDto;
import com.order.order.model.Order;
import com.order.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDto processOrder(OrderDto dto) {
        
        Optional<Order> existingOrder = orderRepository.findByOrderNumber(dto.getOrderNumber());
        if (existingOrder.isPresent()) {
            throw new RuntimeException("Pedido já existe!");
        }
        
        Order order = OrderDto.OrderDtoToEntity(dto);
        order.setItens(order.getItens().stream().peek(item -> item.setOrder(order)).toList());
        
        return OrderDto.OrderEntityToDto(orderRepository.save(order));
    }

    public Optional<OrderDto> getOrderById(Long id) {
        Optional<Order> optional = orderRepository.findById(id);
        if(optional.isPresent()) {        	
        	
        	return Optional.of(OrderDto.OrderEntityToDto(optional.get()));
        }
        return Optional.ofNullable(null);
    }
}

