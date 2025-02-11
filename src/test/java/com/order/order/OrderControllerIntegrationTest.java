package com.order.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarPedidoComSucesso() throws Exception {
        String orderJson = """
        {
          "orderNumber": "999",
          "itens": [
            { "productName": "Teclado", "quantity": 1, "price": 150.00 },
            { "productName": "Mouse", "quantity": 2, "price": 75.00 }
          ]
        }
        """;

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalValue").value(300.00));
    }

    @Test
    void deveRetornar404ParaPedidoInexistente() throws Exception {
        mockMvc.perform(get("/orders/9999"))
                .andExpect(status().isNotFound());
    }

}
