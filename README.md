# Drop 21 — Urban Supply 🛒

Sistema de e-commerce e gestão para loja de periféricos e streetwear, desenvolvido com **Java (Spring Boot)** no backend e **HTML/CSS/JavaScript** puro no frontend.

---

## 📋 Sobre o Projeto

O Drop 21 é uma plataforma completa que simula o funcionamento de uma loja real com dois canais de venda:

- **Loja Online (E-commerce):** Vitrine de produtos, carrinho de compras, finalização de pedidos e acompanhamento pelo cliente.
- **PDV (Ponto de Venda):** Frente de caixa para vendas presenciais no balcão, com abertura/fechamento de caixa.
- **Painel de Gestão:** Cadastro de produtos, controle de estoque e painel de vendas do dia (online + balcão).

---

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 21 + Spring Boot 4.1.1 |
| Banco de Dados | SQLite (via Hibernate/JPA) |
| Segurança | BCrypt (Spring Security Crypto) |
| Frontend | HTML5, CSS3, JavaScript (Vanilla) |
| Build | Maven |
| Versionamento | Git + GitHub |

---

## ✨ Funcionalidades

### Cliente
- Cadastro e login de conta (senhas criptografadas)
- Vitrine de produtos com busca por nome
- Carrinho de compras com adição/remoção de itens
- Finalização de pedido
- Tela "Meus Pedidos" com pagamento (Dinheiro, PIX, Cartão) e cancelamento
- Devolução automática de estoque ao cancelar

### Operador (PDV)
- Abertura de caixa com fundo de troco
- Passagem de produtos por ID
- Registro de venda com forma de pagamento e desconto
- Fechamento de caixa com resumo de vendas

### Dono (Gestão)
- Cadastro, edição e exclusão de produtos
- Controle de estoque em tempo real
- Painel com pedidos da loja online e vendas do balcão
- Limpeza em massa de todos os pedidos no banco (Função Administrativa)

### Controle de Acesso (RBAC)
| Perfil | Acesso |
|--------|--------|
| `ROLE_CLIENTE` | Loja, Meus Pedidos |
| `ROLE_OPERADOR` | Loja, Meus Pedidos, PDV |
| `ROLE_DONO` | Loja, Meus Pedidos, PDV, Gestão |

---

## 📁 Estrutura do Projeto

```
curso-spring/
├── src/main/java/com/produtoapi/
│   ├── client/                    # Frontend (HTML/CSS/JS)
│   │   ├── loja.html              # Vitrine e carrinho
│   │   ├── login.html             # Tela de login
│   │   ├── cadastro.html          # Tela de cadastro
│   │   ├── meus-pedidos.html      # Acompanhamento de pedidos
│   │   ├── pdv.html               # Ponto de Venda
│   │   ├── client-web-listagem.html # Painel de Gestão
│   │   └── styles.css             # Estilo visual (tema escuro, toasts, modais)
│   ├── controller/                # Camada de entrada (REST)
│   │   ├── ProdutoController.java
│   │   ├── PedidoController.java
│   │   ├── UsuarioController.java
│   │   └── CaixaController.java
│   ├── model/                     # Entidades JPA
│   │   ├── Produto.java
│   │   ├── Pedido.java
│   │   ├── ItemPedido.java
│   │   ├── Usuario.java
│   │   ├── Caixa.java
│   │   ├── VendaBalcao.java
│   │   └── ItemVendaBalcao.java
│   ├── repository/                # Acesso ao banco (Spring Data)
│   ├── service/                   # Lógica de negócio
│   └── config/                    # Configurações (DataSeeder)
├── pom.xml                        # Dependências Maven
├── DECISOES.md                    # Decisões técnicas do projeto
└── DIARIO_DE_BORDO.md             # Registro de todas as etapas do desenvolvimento      
└── README.md                      # Apresentação principal do projeto
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Java 21 ou superior
- Maven (ou usar o wrapper `mvnw` incluído)
- IDE de sua preferência (IntelliJ IDEA, VS Code, Eclipse)

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/arthuralmeida-dev/Projeto-Ordem01.git
   ```
2. Abra o projeto na sua IDE.
3. Execute a classe `CursoSpringApplication.java`.
4. O servidor iniciará na porta **8080**.
5. Acesse o frontend abrindo os arquivos HTML diretamente no navegador, ou usando o preview da sua IDE (ex: porta `63342` do IntelliJ ou via Live Server no VS Code).

### Primeiro acesso como Dono
Para promover um usuário a gestor (`ROLE_DONO`), é necessário editar o banco de dados diretamente:
1. Cadastre-se normalmente pela tela de cadastro na loja.
2. Acesse o banco SQLite (`meu_banco_de_dados.db`):
   - **No IntelliJ Ultimate:** Utilize a aba "Database" nativa do lado direito para gerenciar o `.db`.
   - **No IntelliJ Community ou VS Code:** Caso não tenha a versão paga do IntelliJ, recomendamos usar a extensão gratuita *"Database Client"* do VS Code como ferramenta auxiliar para visualizar as tabelas.
3. Na tabela `usuario`, altere manualmente o campo `role` do seu usuário recém-criado de `ROLE_CLIENTE` para `ROLE_DONO`.
4. Faça login novamente — as abas PDV e Gestão aparecerão protegidas no menu.

---

## 🔗 Endpoints da API

### Produtos
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/produtos` | Listar todos |
| GET | `/produtos/{id}` | Buscar por ID |
| GET | `/produtos/buscar?nome=` | Buscar por nome |
| POST | `/produtos` | Criar produto |
| PUT | `/produtos/{id}` | Atualizar produto |
| DELETE | `/produtos/{id}` | Excluir produto |

### Pedidos
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/pedidos` | Listar todos |
| GET | `/pedidos/cliente?email=` | Listar por cliente |
| POST | `/pedidos` | Criar pedido |
| PUT | `/pedidos/{id}/pagar` | Registrar pagamento |
| PUT | `/pedidos/{id}/cancelar` | Cancelar (devolve estoque) |
| DELETE | `/pedidos` | Limpar todos (Apaga o BD de pedidos) |

### Usuários
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/usuarios` | Cadastrar (com encriptação BCrypt) |
| POST | `/usuarios/login` | Login |

### Caixa (PDV)
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/caixa/abrir` | Abrir caixa |
| POST | `/caixa/{id}/venda` | Registrar venda |
| PUT | `/caixa/{id}/fechar` | Fechar caixa |
| GET | `/caixa/vendas` | Listar vendas |

---

## 👨‍💻 Autor

**Arthur Almeida**  
[GitHub](https://github.com/arthuralmeida-dev)
