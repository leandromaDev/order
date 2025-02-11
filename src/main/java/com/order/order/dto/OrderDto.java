package com.order.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.order.order.model.Order;
import com.order.order.model.OrderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {

	private static final long serialVersionUID = 8068419627533460722L;
	
	private Long id;

	@NotBlank(message = "O número do pedido não pode estar vazio")
    private String orderNumber;

    @Size(min = 1, message = "O pedido deve conter pelo menos um item")
    private List<OrderItemDto> itens;

    private BigDecimal totalValue;
    
    public static OrderDto OrderEntityToDto(Order entity) {
    	return OrderDto.builder()
    	 .id(entity.getId())
    	 .orderNumber(entity.getOrderNumber())
    	 .totalValue(entity.getTotalPedido())
    	 .itens(getItemsToDto(entity.getItens()))
    	 .build();
    }
    
    public static Order OrderDtoToEntity(OrderDto dto) {
    	return Order.builder()
    	 .id(dto.getId())
    	 .orderNumber(dto.getOrderNumber())    	 
    	 .itens(getItensToEntity(dto.getItens()))
    	 .build();
    }

	public static List<OrderItemDto> getItemsToDto(List<OrderItem> itens) {
		return itens.stream()
				.map(OrderItemDto::new)
				.toList();
	}

	public static List<OrderItem> getItensToEntity(List<OrderItemDto> itensDto) {
		return itensDto.stream()
				.map(OrderItem::new)
				.toList();
	}

}
