# Drop 21 — Urban Supply 🛒

Sistema de e-commerce e gestão para loja de streetwear e periféricos gamer, desenvolvido com **Java (Spring Boot)** no backend e **HTML5/CSS3/JavaScript (Vanilla)** no frontend.

---

## 🌐 Acesse o Projeto Online

O projeto está totalmente hospedado e pronto para uso:

- 🔗 **Loja & Frontend (Vercel):** [https://drop-21.vercel.app](https://drop-21.vercel.app)
- ⚙️ **API Backend (Render):** [https://drop-21.onrender.com](https://drop-21.onrender.com)

> 💡 *Nota: Por estar no plano gratuito do Render, caso o backend esteja em modo de repouso (inativo por mais de 15 minutos), o primeiro acesso pode levar cerca de 40 a 50 segundos para inicializar os contêineres na nuvem.*

---

## 📋 Sobre o Projeto

O Drop 21 simula o funcionamento completo de uma loja moderna com múltiplos canais de venda integrados à mesma base de dados:

- **Loja Online (E-commerce):** Vitrine de produtos com busca dinâmica, carrinho de compras, finalização de pedidos e acompanhamento de status pelo cliente.
- **PDV (Ponto de Venda):** Frente de caixa para vendas físicas presenciais no balcão, com abertura, controle de troco e fechamento de caixa.
- **Painel de Gestão:** Cadastro, edição e exclusão de produtos, controle de estoque em tempo real e monitoramento centralizado de vendas (online + balcão).

---

## 🛠️ Tecnologias Utilizadas

| Camada | Tecnologia |
|--------|-----------|
| **Backend** | Java 21 + Spring Boot |
| **Banco de Dados** | SQLite (via Hibernate/Spring Data JPA) |
| **Segurança** | BCrypt (Spring Security Crypto) + Controle de Acesso (RBAC) |
| **Frontend** | HTML5, CSS3 moderno (responsivo), JavaScript Puro (Vanilla) |
| **Deploy / Nuvem** | Vercel (Frontend) + Render com Docker (Backend) |
| **Build & Gerenciamento** | Maven |
| **Versionamento** | Git + GitHub |

---

## ✨ Funcionalidades

### 👤 Cliente
- Cadastro de conta e autenticação com senhas criptografadas via BCrypt.
- Vitrine de produtos responsiva com busca por nome em tempo real.
- Carrinho de compras dinâmico com cálculo de subtotal e total.
- Painel "Meus Pedidos" para acompanhar status, pagar ou cancelar compras.
- Devolução automática de produtos ao estoque em caso de cancelamento.

### 💼 Operador (PDV / Caixa)
- Abertura de caixa com definição de fundo de troco inicial.
- Lançamento rápido de itens por código/ID.
- Aplicação de descontos por item ou no valor geral da venda.
- Fechamento de sessão de caixa com resumo financeiro de vendas do turno.

### 👑 Dono (Gestão & Administração)
- Cadastro, edição de preço/quantidade e exclusão de produtos do catálogo.
- Monitoramento de níveis de estoque com bloqueio automático contra venda sem saldo.
- Visualização unificada de pedidos da loja virtual e vendas efetuadas no PDV.

---

## 🔐 Perfis de Acesso (RBAC) & Credenciais de Teste

O sistema já é inicializado automaticamente com dados e contas de teste através do `DataSeeder`:

| Perfil | Nível de Permissão | E-mail | Senha |
|---|---|---|---|
| **Dono (Gestor)** | Loja, Meus Pedidos, PDV e Painel de Gestão | `admin@drop21.com` | `123456` |
| **Operador (Caixa)** | Loja, Meus Pedidos e Frente de Caixa (PDV) | `operador@drop21.com` | `123456` |
| **Cliente** | Acesso ao catálogo da Loja e Meus Pedidos | *(Criado na tela de Cadastro)* | *(Senha pessoal)* |

---

## 🚀 Como Rodar o Projeto Localmente

### Pré-requisitos
- **Java 21** ou superior
- **Maven** instalado (ou utilizar o `./mvnw` incluso)
- IDE de sua preferência (VS Code, IntelliJ IDEA, Eclipse)

### Passos de Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/arthuralmeida-dev/Projeto-Orden01.git
   ```
2. Abra o projeto na sua IDE.
3. Execute a classe principal: `CursoSpringApplication.java`.
4. O servidor iniciará localmente na porta **8080**.

---

### ⚠️ IMPORTANTE: Ajuste das URLs da API (`fetch`)

> **Atenção para execução local:**
> Como o projeto está atualmente em produção na nuvem, todos os arquivos do frontend (`src/main/java/com/produtoapi/client/*.html`) estão configurados por padrão para consumir a API hospedada no Render:
> ```javascript
> https://drop-21.onrender.com/
> ```
> 
> Se você optar por rodar o backend localmente na sua máquina (`http://localhost:8080`), você deve alterar as URLs dos arquivos HTML para apontarem para o seu `localhost`.
> 
> **Como fazer a troca rápida via Localizar e Substituir:**
> - No **VS Code:** Pressione `Ctrl + Shift + F`
> - No **IntelliJ:** Pressione `Ctrl + Shift + R`
> 
> | Campo | Valor |
> |---|---|
> | **Localizar (Search):** | `https://drop-21.onrender.com/` |
> | **Substituir por (Replace):** | `http://localhost:8080/` |
> 
> Clique em **"Replace All" (Substituir Tudo)** e salve os arquivos.

---

### Primeiro acesso como Dono (via Banco de Dados Local)
Caso queira promover manualmente qualquer usuário para administrador (`ROLE_DONO`):
1. Cadastre um novo usuário pela tela de cadastro.
2. Abra o arquivo de banco de dados SQLite (`meu_banco_de_dados.db` gerado na raiz):
   - **IntelliJ Ultimate:** Utilize a aba nativa *Database*.
   - **VS Code / Outros:** Utilize a extensão gratuita *Database Client* ou o *DB Browser for SQLite*.
3. Na tabela `usuario`, altere a coluna `role` de `ROLE_CLIENTE` para `ROLE_DONO`.
4. Ao relogar, as opções restritas de Gestão e PDV estarão liberadas no menu.

---

## 📁 Estrutura de Pastas

```
curso-spring/
├── src/main/java/com/produtoapi/
│   ├── client/                      # Frontend (HTML5, CSS3, JavaScript Vanilla)
│   │   ├── index.html               # Vitrine online e checkout
│   │   ├── login.html               # Autenticação de usuários
│   │   ├── cadastro.html            # Cadastro de novos clientes
│   │   ├── meus-pedidos.html        # Painel do cliente e acompanhamento
│   │   ├── pdv.html                 # Ponto de venda para operadores
│   │   ├── client-web-listagem.html # Dashboard de gestão e produtos
│   │   └── styles.css               # Folha de estilos responsiva
│   ├── controller/                  # Controladores REST (@CrossOrigin liberado)
│   │   ├── ProdutoController.java
│   │   ├── PedidoController.java
│   │   ├── UsuarioController.java
│   │   └── CaixaController.java
│   ├── model/                       # Entidades JPA (Mapeamento de tabelas)
│   ├── repository/                  # Interfaces de acesso ao banco (Spring Data JPA)
│   ├── service/                     # Regras de negócio e validações de estoque
│   └── config/                      # Injeção inicial de dados (DataSeeder)
├── Dockerfile                       # Arquivo de build de contêiner para o Render
├── pom.xml                          # Dependências e plugins do Maven
└── README.md                        # Documentação do projeto
```

---

## 🔗 Principais Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/produtos` | Listagem de produtos da vitrine |
| `GET` | `/produtos/buscar?nome=` | Busca dinâmica de produtos por nome |
| `POST` | `/produtos` | Cadastro de novos produtos |
| `PUT` | `/produtos/{id}` | Atualização de produto e estoque |
| `DELETE` | `/produtos/{id}` | Exclusão de produto |
| `POST` | `/usuarios` | Cadastro de usuário com criptografia BCrypt |
| `POST` | `/usuarios/login` | Autenticação e retorno de sessão |
| `GET` | `/pedidos` | Listagem geral de pedidos |
| `GET` | `/pedidos/cliente?email=` | Listagem de pedidos de um cliente específico |
| `POST` | `/pedidos` | Criação de novo pedido |
| `PUT` | `/pedidos/{id}/pagar` | Atualização do status para pago |
| `PUT` | `/pedidos/{id}/cancelar` | Cancelamento com estorno automático no estoque |
| `POST` | `/caixa/abrir` | Abertura de caixa do PDV |
| `POST` | `/caixa/{id}/venda` | Registro de venda no balcão |
| `PUT` | `/caixa/{id}/fechar` | Fechamento e apuração de caixa |

---

## 👨‍💻 Desenvolvedor

**Arthur Almeida**  
- 💼 [LinkedIn](https://www.linkedin.com/)  
- 🐙 [GitHub](https://github.com/arthuralmeida-dev)
