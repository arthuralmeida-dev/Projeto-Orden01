# DECISÕES TÉCNICAS — Projeto Drop 21

**Aluno:** Arthur Almeida  
**Disciplina:** Curso Spring  
**Data:** 15/09/2026

---

## 1. Banco de Dados: SQLite

**Decisão:** Utilizar SQLite como sistema de banco de dados relacional.

**Justificativa:** O SQLite é um banco de dados embutido (não precisa de instalação separada de servidor). Ele armazena tudo em um único arquivo `.db`, o que facilita o desenvolvimento local e a portabilidade do projeto. Para o escopo de uma loja virtual de pequeno porte, o SQLite atende perfeitamente — ele suporta operações CRUD completas e é compatível com o Spring Data JPA via Hibernate. A configuração no `application.properties` é mínima.

**Trade-off:** O SQLite não suporta acessos simultâneos de escrita tão bem quanto bancos maiores (PostgreSQL, MySQL). Em um cenário de produção com alto volume, seria necessário migrar. Para o projeto acadêmico, essa limitação não impacta.

---

## 2. Arquitetura: Frontend e Backend Separados (API REST)

**Decisão:** Separar completamente o frontend (HTML/CSS/JavaScript puro) do backend (Java 21 + Spring Boot 4.1.1), comunicando-os exclusivamente por requisições HTTP (API REST).

**Justificativa:** 
1. **Independência:** O frontend pode ser testado separadamente do backend.
2. **Clareza:** Cada camada tem uma responsabilidade única.
3. **Aprendizado:** Demonstração prática da comunicação HTTP cliente-servidor.

**Trade-off:** A separação exige resolver o problema de CORS (resolvido com a anotação `@CrossOrigin("*")`).

---

## 3. Controle de Acesso: RBAC com localStorage

**Decisão:** Implementar controle de acesso baseado em papéis (RBAC) utilizando o campo `role` na entidade `Usuario` e `localStorage` no navegador.

**Justificativa:** O sistema possui três perfis de acesso:
| Role | Pode acessar |
|------|-------------|
| `ROLE_CLIENTE` | Loja, Meus Pedidos |
| `ROLE_OPERADOR` | Loja, Meus Pedidos, PDV |
| `ROLE_DONO` | Loja, Meus Pedidos, PDV, Gestão |

O JavaScript verifica o `role` no `localStorage` e monta o menu dinamicamente. Páginas protegidas (Gestão, PDV) também verificam o `role` via JS ao carregar, redirecionando usuários não autorizados.

**Trade-off:** A verificação acontece apenas no frontend, permitindo manipulação via DevTools. Implementações ideais usariam Spring Security (ver tópico 7).

---

## 4. Gestão de Estoque: Baixa Automática e Devolução

**Decisão:** Automatizar a baixa de estoque na criação do pedido e devolução ao cancelar.

**Justificativa:** Isso garante integridade: o estoque sempre reflete a realidade das vendas. Ao cancelar um pedido, o fluxo inverso ocorre devolvendo a quantidade exata ao estoque de cada produto.

**Trade-off:** Exigiu a configuração do `@OneToMany` com `FetchType.EAGER` em `Pedido`, para carregar a lista de itens ao buscar um pedido para cancelamento, evitando `StackOverflowError` ou dados ausentes.

---

## 5. Sistema de Pedidos: Fluxo e Limpeza (Gestão)

**Decisão:** Implementar status (PENDENTE, PAGO, CANCELADO) separando a compra do pagamento, e fornecer ferramentas gerenciais para deleção em massa.

**Justificativa:** 
O cliente finaliza a compra sem pagamento e usa a área "Meus Pedidos" para pagar (Dinheiro, PIX, Cartão) ou cancelar.
Para os gerentes (ROLE_DONO), foi adicionada uma função de "Limpar Todos os Pedidos" para evitar congestionamento no banco de testes. Essa operação exige o cuidado de apagar os itens dependentes (ItemPedido) antes dos Pedidos principais para manter a integridade referencial.

---

## 6. Serialização JSON: Uso do @JsonIgnore

**Decisão:** Utilizar `@JsonIgnore` no campo `pedido` dentro de `ItemPedido`.

**Justificativa:** Evita serialização circular (loop infinito) quando o Spring Boot converte objetos Java para JSON, impedindo o `StackOverflowError`. Apenas a saída JSON é simplificada, enquanto a relação no banco de dados continua intacta.

---

## 7. Segurança: Criptografia de Senhas (BCrypt)

**Decisão:** Implementar `BCryptPasswordEncoder` para salvar as senhas dos usuários criptografadas no banco de dados.

**Justificativa:** Salvar senhas em texto puro é uma falha grave de segurança. Com o BCrypt, mesmo que o banco de dados seja comprometido, as senhas originais não podem ser descobertas (criptografia irreversível).

**Melhorias Futuras (Trade-offs de Escopo):** 
Para um sistema de produção, seria necessário implementar autenticação completa no backend via tokens JWT (JSON Web Tokens) através do Spring Security. Isso protegeria todos os endpoints REST de acesso direto não autorizado, e a verificação do `ROLE` passaria a ser feita pelo próprio servidor, e não apenas pelo JavaScript (localStorage). Devido à complexidade arquitetural, optou-se por focar na segurança básica do banco (BCrypt) para o escopo atual.
