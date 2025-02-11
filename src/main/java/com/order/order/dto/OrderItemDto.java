package com.order.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.order.model.Order;
import com.order.order.model.OrderItem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto implements Serializable{

	private static final long serialVersionUID = -573086820687876104L;
	
	private Long id;

	@JsonIgnore
    private Order order;

    @NotBlank(message = "O nome do produto não pode estar vazio")
    private String productName;
    
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private int quantity;

    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private BigDecimal price;

	public OrderItemDto(OrderItem entity) {
		this.id = entity.getId();
		this.order = entity.getOrder();
		this.productName = entity.getProductName();
		this.quantity = entity.getQuantity();
		this.price = entity.getPrice();
	}
	
}
