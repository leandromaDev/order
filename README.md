Aplicação SpringBoot de pedidos com um método POST e um GET:

Testes
Exemplo de post:
curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--data '{
  
  "orderNumber": "12348",
  "itens": [
    {
      
      "productName": "Mouse Gamer",
      "quantity": 2,
      "price": 150.00
    },
    {
      
      "productName": "Teclado Mecânico",
      "quantity": 1,
      "price": 250.00
    },
    {
      
      "productName": "Monitor Full HD",
      "quantity": 1,
      "price": 800.00
    }
  ]
}
'

Exemplo de get:
curl --location 'http://localhost:8080/orders/1'
