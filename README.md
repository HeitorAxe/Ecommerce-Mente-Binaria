# API Products
 
Bem-vindo à API de Produtos, desenvolvida em Spring Boot. Esta API fornece endpoints para gerenciar informações sobre produtos.
 
## Requisitos
 
- Java 17 
- Banco de dados MySQL 
 
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
 
## Endpoints da API
 
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

### Tratamento de Exceções

A API de Produtos pode gerar diferentes códigos de resposta HTTP em caso de exceções. Aqui estão algumas situações que podem ocorrer:

```json
{
  "error": "400 Bad Request",
  "message": "The request contains invalid data."
}

{
  "error": "404 Not Found",
  "message": "The product with the specified ID was not found."
}

{
  "error": "500 Internal Server Error",
  "message": "An internal server error occurred. Check the logs for more details."
}


```

### Importantes
```json
{

"Certifique-se de que a descrição contenha pelo menos 10 caracteres e que o preço seja um valor não negativo."

}
```
