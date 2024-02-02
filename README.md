# API de E-commerce
 
Bem-vindo à API de E-commerce, uma solução robusta desenvolvida em Spring Boot para gerenciar informações de produtos e pedidos. Esta API oferece endpoints para realizar operações como adição, recuperação, atualização e exclusão de produtos e pedidos.

# Visão Geral

A API de E-commerce permite que você gerencie facilmente os produtos disponíveis para venda e os pedidos feitos por clientes. Com uma interface simples e poderosa, você pode integrar facilmente esta API em sua aplicação para construir uma experiência de comércio eletrônico completa.


## Colaboradores

MENTE BINÁRIA
-------------------------------------
| E-mail              | Usuário GitHub |
|---------------------|----------------|
| jeffley.garcon.pb@compasso.com.br   | jeffleyg    |
| heitor.machado.pb@compasso.com.br     | HeitorAxe     |
| loude.sime.pb@compasso.com.br     | loudedje    |
|luiz.dias.pb@compasso.com.br     | Luizh97     |
| pablo.haddad.pb@compasso.com.br   |pablitohaddad   |
| rafael.luz@compasso.com.br    | Orientador     |

 
## Requisitos
 
- Java 17 
- Banco de dados MySQL
- Spring Boot
 
## Configuração
 
1. Clone o repositório:
 
```bash
git clone https://https://github.com/HeitorAxe/Ecommerce-Mente-Binaria
```
 
2. Configure o banco de dados no arquivo `application.properties`.
 
3. Execute a aplicação:
 
```bash
mvn spring-boot:run
```
 
A aplicação estará disponível em http://localhost:8080/docs-EcommerceMenteBinaria.html.
 
## Endpoints da API de Produtos
 
### `GET /products`
 
Recupera a lista de todos os produtos.
 
**Exemplo de resposta:**
```json
{
  "produtos": [
    {
      "id": 1,
      "nanme": "TV",
       "description": "TV grande e boa qualidade "
      "price": 3000.99
    },
    
  ]
}
```
 
### `GET /products/{id}`
 
Recupera um produto específico por ID.
 
**Exemplo de resposta:**
```json
{
      "id": 1,
      "nanme": "TV",
       "description": "TV grande e boa qualidade "
      "price": 3000.99
    },
```
 
### `POST /products`
 
Adiciona um novo produto.
 
**Corpo da solicitação:**
```json
{
  "name": "Smartphone",
  "description": ""
  "price": 1900.99
}
```

### `DELETE /products/{id}`

Excluir um produto específico por ID.

**Exemplo de resposta:**
```json
{
  204 No Content
}
```

### `PUT /products/{id}`

Atualizar informações de um produto específico por ID.

**Corpo da solicitação:**
```json
{
  "name": "Nova TV",
  "description": "TV moderna e inteligente",
  "price": 3500.99
}
```
### `GET /products/page`
**Corpo da solicitação:**

Recuperar uma lista paginada de produtos.

```json
{
  "products": [
    // Lista de produtos da página solicitada
  ],
  "page": 1,
  "totalPages": 5
}
```



## Endpoints da API de Pedidos

### `GET /orders`

Recupera a lista de todos os pedidos.

**Exemplo de resposta:**
```json
{
      "products": [
          {
              "productId": 3,
              "quantity": 129
          }
      ],
      "address": {
          "number": 235,
          "complement": "ap209",
          "postalCode": "89803108",
          "city": "Chapecó",
          "street": "Rua Castro Alves - E",
          "state": "SC"
      },
      "paymentMethod": "BANK_TRANSFER",
      "orderStatus": "CONFIRMED",
      "totalValue": 3310.0,
      "subTotalValue": 3310.0,
      "creationDate": "2024-02-01T13:58:58.600884",
      "cancelationDate": null,
      "cancelReason": null
}
```

### `GET /orders/{id}`

Recupera um pedido específico por ID.

**Exemplo de resposta:**
```json
{
  "products": [
        {
            "productId": 2,
            "quantity": 6
        },
        {
            "productId": 4,
            "quantity": 5
        }
    ],
    "address": {
        "number": 10,
        "complement": "Ap 101",
        "postalCode": "89803450",
        "city": "Chapecó",
        "street": "Travessa Nicolau Vergueiro",
        "state": "SC"
    },
    "paymentMethod": "PIX",
    "orderStatus": "CONFIRMED",
    "totalValue": 4807.0,
    "subTotalValue": 5060.0,
    "creationDate": "2024-02-01T13:59:24.440834",
    "cancelationDate": null,
    "cancelReason": null
}
```

### `POST /orders`

Cria um novo pedido.

**Corpo da solicitação:**
```json
{
 "products": [
        {
            "productId": 4,
            "quantity": 5
        },
        {
            "productId": 2,
            "quantity": 6
        }
    ],
    "address": {
        "number": 10,
        "complement": "Ap 101",
        "postalCode": "89803450",
        "state": "SC"
    },
    "paymentMethod": "PIX"
}
```

### `DELETE /orders/{id}`

Exclui um pedido específico por ID.

**Corpo da solicitação:**
```json
{
  "cancelReason":"mensagem da raazão do delete"
}
```

**Exemplo de resposta:**
```json
{
   "products": [
        {
            "productId": 3,
            "quantity": 129
        }
    ],
    "address": {
        "number": 235,
        "complement": "ap209",
        "postalCode": "89803108",
        "city": "Chapecó",
        "street": "Rua Castro Alves - E",
        "state": "SC"
    },
    "paymentMethod": "BANK_TRANSFER",
    "orderStatus": "CANCELED",
    "totalValue": 3310.0,
    "subTotalValue": 3310.0,
    "creationDate": "2024-02-01T13:58:58.600884",
    "cancelationDate": "2024-02-01T19:22:49.915785",
    "cancelReason": "mensagem da raazão do delete"
}
```

### `PUT /orders/{id}`

Atualiza informações de um pedido específico por ID.

**Corpo da solicitação:**
```json
{
  "products": [
        {
            "productId": 3,
            "quantity": 109
        },
       {
            "productId": 3,
            "quantity": 20
        }
    ],
    "address": {
        "number": 235,
        "complement": "ap209",
        "postalCode": "89803108",
        "state": "SC"
    },
    "paymentMethod": "BANK_TRANSFER"
}
```

### `GET /orders`

Recupera a lista de pedidos.
**Exemplo de resposta:**

```json
{
        "id": 1,
        "products": [
            {
                "productId": 3,
                "quantity": 103
            }
        ],
        "address": {
            "number": 235,
            "complement": "ap209",
            "postalCode": "89803108",
            "city": "Chapecó",
            "street": "Rua Castro Alves - E",
            "state": "SC"
        },
        "paymentMethod": "BANK_TRANSFER",
        "orderStatus": "CANCELED",
        "totalValue": 1150.0,
        "subTotalValue": 1150.0,
        "creationDate": "2024-01-31T09:33:47.965624",
        "cancelationDate": "2024-01-31T21:46:58.186494",
        "cancelReason": "oke"
    },
    {
        "id": 2,
        "products": [
            {
                "productId": 3,
                "quantity": 103
            }
        ],
        "address": {
            "number": 235,
            "complement": "ap209",
            "postalCode": "89803108",
            "city": "Chapecó",
            "street": "Rua Castro Alves - E",
            "state": "SC"
        },
        "paymentMethod": "BANK_TRANSFER",
        "orderStatus": "CANCELED",
        "totalValue": 3360.0,
        "subTotalValue": 3360.0,
        "creationDate": "2024-01-31T09:35:16.955584",
        "cancelationDate": "2024-01-31T21:46:53.474858",
        "cancelReason": "oke"
    }
```

### `GET /orders/page`

Recupera uma lista paginada de pedidos.

**Corpo da solicitação no Swagger:**
```json
{
  "page": 0,
  "size": 1,
  "sort": [
    "string"
  ]
}
```

**Exemplo de resposta:**
```json
    {
        "id": 1,
        "products": [
            {
                "productId": 3,
                "quantity": 103
            }
        ],
        "address": {
            "number": 235,
            "complement": "ap209",
            "postalCode": "89803108",
            "city": "Chapecó",
            "street": "Rua Castro Alves - E",
            "state": "SC"
        },
        "paymentMethod": "BANK_TRANSFER",
        "orderStatus": "CANCELED",
        "totalValue": 1150.0,
        "subTotalValue": 1150.0,
        "creationDate": "2024-01-31T09:33:47.965624",
        "cancelationDate": "2024-01-31T21:46:58.186494",
        "cancelReason": "oke"
    }
```

### Tratamento de Exceções

A API de Produtos e Pedidos podem gerar diferentes códigos de resposta HTTP em caso de exceções. Aqui estão algumas situações que podem ocorrer:

```json
{
  "path": "/orders/100",
    "method": "GET",
    "status": 404,
    "statusText": "Not Found",
    "message": "Order 100 not found"
}

{
  "path": "/orders/2a",
    "method": "GET",
    "status": 400,
    "statusText": "Bad Request",
    "message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; For input string: \"2a\""
}

{
  "path": "/orders",
    "method": "PUT",
    "status": 405,
    "statusText": "Method Not Allowed",
    "message": "Request method 'PUT' is not supported"
}
{
  "path": "/orders/6",
    "method": "DELETE",
    "status": 422,
    "statusText": "Unprocessable Entity",
    "message": "Invalid Fields",
    "details": {
        "cancelReason": "cancel reason cannot be empty"
    }
}
```

### Importante

```json
{
  
"Certifique-se de que o ID do produto fornecido existe e de que todos os campos estejam preenchidos corretamente."
"Certifique-se de que a descrição contenha pelo menos 10 caracteres e que o preço seja um valor não negativo."
}
```

Este README serve como um guia para utilizar a API de Pedidos, fornecendo informações sobre como configurar, usar e entender os endpoints disponíveis.



