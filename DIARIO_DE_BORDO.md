# DIÁRIO DE BORDO — Projeto Drop 21

**Aluno:** Arthur Almeida  
**Disciplina:** Curso Spring  
**Período:** Setembro/2026

---

## Etapa 1 — Configuração do Ambiente e Estrutura Inicial

**O que foi feito:**
- Criação do projeto Spring Boot via Spring Initializr com as dependências: Spring Web, Spring Data JPA e SQLite.
- Configuração do `application.properties` para utilizar o banco SQLite (`meu_banco_de_dados.db`).
- Estruturação do projeto em camadas MVC: `model`, `repository`, `service`, `controller` e `client` (frontend).

**Dificuldades encontradas:**
- A necessidade de gerenciar o banco de dados diretamente sem possuir o IntelliJ Ultimate (versão paga). A solução encontrada foi utilizar a extensão gratuita "Database Client" do VS Code como ferramenta auxiliar exclusivamente para visualizar e editar as tabelas (como promover usuários para ROLE_DONO), mantendo o IntelliJ Community focado em rodar o backend Java sem interrupções.

**Aprendizado:**
- Entendi a diferença entre IDE (editor de código) e ferramentas clientes de banco de dados, e como integrá-las de forma fluida no ambiente de desenvolvimento.

---

## Etapa 2 — CRUD de Produtos

**O que foi feito:**
- Criação da entidade `Produto.java` com campos: id, nome, quantidade, preco, status.
- Implementação do `ProdutoRepository`, `ProdutoService` e `ProdutoController` com operações CRUD completas (GET, POST, PUT, DELETE).
- Criação da página de gestão (`client-web-listagem.html`) com formulário de cadastro e tabela de inventário.

**Dificuldades encontradas:**
- Erro "Failed to fetch" ao salvar produtos. Descobri que era causado pela falta da anotação `@CrossOrigin("*")` no Controller. Como o frontend roda na porta 63342 e o backend na 8080, o navegador bloqueava a requisição por política de CORS.

**Aprendizado:**
- Compreendi o mecanismo de CORS (Cross-Origin Resource Sharing) e por que navegadores bloqueiam requisições entre origens diferentes por segurança.

---

## Etapa 3 — Sistema de Pedidos e Estoque

**O que foi feito:**
- Criação das entidades `Pedido.java` e `ItemPedido.java` com relacionamento `@OneToMany` / `@ManyToOne`.
- Implementação da lógica de criação de pedidos no `PedidoService`, incluindo: busca do produto no banco, verificação de estoque, baixa automática da quantidade, cálculo de subtotais e total.
- Construção da vitrine e carrinho de compras na `loja.html`.

**Dificuldades encontradas:**
- Ao listar pedidos com seus itens, o sistema entrava em loop infinito (StackOverflowError). O `Pedido` referenciava o `ItemPedido`, que referenciava de volta o `Pedido`, infinitamente. Resolvido com `@JsonIgnore` no campo `pedido` dentro de `ItemPedido`.
- Os itens do pedido não estavam sendo salvos no banco porque faltava `pedido.setItens(itens)` antes do `save()`. O JPA precisa que ambos os lados do relacionamento estejam conectados para o cascade funcionar.

**Aprendizado:**
- Entendi a diferença entre serialização (Java → JSON) e persistência (Java → Banco). O `@JsonIgnore` afeta apenas a serialização, sem impactar o banco de dados.
- Aprendi que relacionamentos bidirecionais (`@OneToMany` + `@ManyToOne`) precisam ser sincronizados nos dois lados.

---

## Etapa 4 — PDV (Ponto de Venda)

**O que foi feito:**
- Criação das entidades `Caixa.java`, `VendaBalcao.java` e `ItemVendaBalcao.java`.
- Implementação dos serviços de abertura de caixa, registro de venda e fechamento de caixa com resumo.
- Construção da interface do PDV (`pdv.html`) com duas telas: abertura de caixa e frente de caixa.
- Adição da proteção de rota (`ROLE_OPERADOR` e `ROLE_DONO`) para impedir que clientes (ou usuários não logados) acessem a URL do PDV diretamente.

**Aprendizado:**
- Aprendi a construir um fluxo de múltiplas telas na mesma página HTML, alternando visibilidade com `display: none/block` via JavaScript, além da proteção de rotas no cliente.

---

## Etapa 5 — Frontend com Tema Visual Escuro

**O que foi feito:**
- Criação do arquivo `styles.css` com tema escuro profissional, inspirado em e-commerces como KaBuM!.
- Aplicação de variáveis CSS (`--bg`, `--surface`, `--success`, `--red`, etc.) para manter consistência visual em todas as páginas.
- Seção hero na loja com tipografia grande e logo estilizado.

**Aprendizado:**
- Entendi o poder das variáveis CSS para manter um design system consistente. Mudar uma cor no `:root` atualiza automaticamente todos os elementos que a referenciam.

---

## Etapa 6 — Autenticação e Controle de Acesso (RBAC)

**O que foi feito:**
- Criação da entidade `Usuario.java` com campo `role` (valores: ROLE_CLIENTE, ROLE_OPERADOR, ROLE_DONO).
- Implementação de cadastro e login no `UsuarioController` (com endpoint `POST /usuarios/login`).
- Criação das páginas `cadastro.html` e `login.html`.
- Implementação do menu dinâmico via JavaScript: a função `carregarMenu()` lê o `localStorage`, verifica o `role` do usuário logado e monta o menu exibindo apenas as abas permitidas para aquele perfil.
- Proteção da página de Gestão: mesmo digitando a URL diretamente, o JavaScript verifica o role e expulsa usuários não autorizados.

**Dificuldades encontradas:**
- Após o cadastro, o usuário não ficava logado automaticamente. Resolvi adicionando `localStorage.setItem('usuarioLogado', JSON.stringify(...))` no bloco de sucesso do cadastro.
- Erros com cached login no `localStorage` após atualização de BD: aprendi a necessidade de re-login (ou `localStorage.clear()`) para puxar o token com o status de permissões atualizados.
- Atribuir o papel de DONO requeria edição manual do banco. Na ausência do IntelliJ Ultimate, contornamos utilizando o VS Code como cliente de DB alternativo. Em um sistema de produção, seria necessário criar um painel administrativo com rotas específicas para isso.

**Aprendizado:**
- Compreendi o conceito de RBAC (Role-Based Access Control) e como ele se aplica no frontend (quem pode ver quais elementos da interface e URLs).
- Entendi o papel do `localStorage` como mecanismo de persistência de sessão no lado do cliente.

---

## Etapa 7 — Tela "Meus Pedidos" (Pagamento e Cancelamento)

**O que foi feito:**
- Adição dos campos `emailCliente` e `status` (PENDENTE/PAGO/CANCELADO) na entidade `Pedido`.
- Criação do endpoint `GET /pedidos/cliente?email=` para buscar pedidos de um cliente específico.
- Criação do endpoint `PUT /pedidos/{id}/pagar` para registrar o pagamento.
- Criação do endpoint `PUT /pedidos/{id}/cancelar` com devolução automática de estoque.
- Construção da página `meus-pedidos.html` com tabela interativa, seletor de forma de pagamento e botões de Pagar/Cancelar, e barra de navegação customizada.

**Dificuldades encontradas:**
- Ao cancelar, o estoque não era devolvido. O problema estava no `FetchType.LAZY` padrão do `@OneToMany` — ao buscar o pedido para cancelar, a lista de itens vinha vazia. Resolvi configurando `fetch = FetchType.EAGER`.

**Aprendizado:**
- Entendi a diferença entre `FetchType.LAZY` e `FetchType.EAGER`. O EAGER é necessário quando precisamos acessar os itens fora do contexto da transação original.

---

## Etapa 8 — UX: Toasts e Modais Customizados

**O que foi feito:**
- Substituição dos `alert()` nativos do navegador por toasts estilizados (notificações que aparecem no canto e desaparecem sozinhas).
- Substituição dos `confirm()` nativos por modais customizados com botões "Confirmar" e "Cancelar" estilizados.
- Uso de `async/await` com Promises para manter o comportamento síncrono do confirm.

**Aprendizado:**
- Entendi o conceito de `async/await`: o JavaScript é assíncrono por natureza, e o `await` permite "pausar" a execução até que uma Promise seja resolvida (ex: esperar clique do modal).

---

## Etapa 9 — Segurança, Limpeza de Dados e Ajustes Finais

**O que foi feito:**
- Implementação da dependência `spring-security-crypto` no `pom.xml`.
- Aplicação de `BCryptPasswordEncoder` no `UsuarioController` para encriptar a senha durante o Cadastro e verificar a hash no Login.
- Adição da função administrativa "Limpar Todos os Pedidos" para que usuários ROLE_DONO pudessem esvaziar a tabela online via `DELETE` (apagando `ItemPedido` em cascade).
- Adição do status dinâmico do usuário logado na tela "Meus Pedidos" e opção simplificada de deslogar ("Sair") limpando o local storage da sessão ativa.

**Dificuldades encontradas:**
- O banco de dados precisou ser recriado (wipe da tabela `usuario`) pois as antigas senhas, em modo plaintext, entravam em conflito com o matcher de `BCrypt`. 

**Aprendizado:**
- Apenas esconder a senha visualmente não é segurança; uma arquitetura sólida não confia nem naqueles que têm acesso de root ao banco de dados, portanto, transformar um plaintext em um Hash irreversível (BCrypt) previne graves incidentes na exposição dos registros.
- Percebi as limitações do localStorage e como a proteção genuína necessitaria de implementação backend (JWT), que foi deixada como melhoria arquitetural futura.
