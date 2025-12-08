# Coworking-system
# CoWorking Manager — Sistema de Gerenciamento de Espaços Compartilhados
### Projeto Final – Programação Orientada a Objetos (POO) – IFPB 2025.2

## Sobre o Projeto
O CoWorking Manager é um sistema de gerenciamento de espaços compartilhados desenvolvido como projeto final da disciplina Programação Orientada a Objetos (POO).

Seu objetivo é aplicar, de forma prática, os conceitos de POO, tais como:

- Abstração
- Encapsulamento
- Herança
- Polimorfismo
- Interfaces
- Exceções personalizadas
- Persistência de dados (SQLite)

O sistema permite que um operador realize todas as operações necessárias para administrar um ambiente de coworking: cadastrar espaços, criar reservas, cancelar, registrar pagamentos e visualizar relatórios.

---

## Arquitetura Utilizada
O projeto segue o padrão MVC, com os pacotes:

model/ → classes de domínio
dao/ → acesso ao banco SQLite
service/ → regras de negócio e validações
view/ → menus e interação com o usuário


---

## Persistência de Dados (SQLite)
Embora o enunciado permita JSON, este projeto utiliza SQLite, garantindo:

- Armazenamento estruturado
- Operações transacionais
- Menos problemas de concorrência
- Banco leve e distribuído em um único arquivo

Tabelas utilizadas:

- espaços
- reservas
- pagamentos

---

## Funcionalidades Implementadas

### 1. Cadastro de Espaços
Tipos suportados:

- Sala de Reunião — taxa opcional de projetor (ex.: R$ 15,00)
- Cabine Individual — desconto automático de 10% para reservas superiores a 4 horas
- Auditório — taxa fixa de evento (ex.: R$ 100,00)

A classe abstrata `Espaco` possui:

- id
- nome
- capacidade
- disponivel
- precoPorHora
- `calcularCustoReserva()` (método abstrato)

Operações:

- cadastrar
- editar
- listar
- remover

---

### 2. Reservas
Cada reserva contém:

- espaço associado
- data/hora de início
- data/hora de fim
- valor calculado
- status (ativa, concluída ou cancelada)

Validações:

- Impede horário final anterior ao inicial
- Impede reservas sobrepostas
- Calcula custo conforme o tipo de espaço
- Atualiza disponibilidade automaticamente

---

### 3. Cancelamentos
Regras aplicadas:

- Cancelamento até 24h antes: sem taxa
- Cancelamento com menos de 24h: taxa de 20%
- Atualização do status e da disponibilidade

---

### 4. Pagamentos
Cada reserva concluída exige um pagamento.

Campos:

- id
- reserva_id
- valor_pago
- data
- método (Pix, Cartão ou Dinheiro)

---

### 5. Relatórios
Relatórios exibidos em tela:

1. Reservas realizadas em um período
2. Faturamento por tipo de espaço
3. Utilização por espaço
4. Ranking dos espaços mais utilizados

---

## Requisitos Técnicos Atendidos

- Java
- Padrão MVC
- Classe abstrata `Espaco`
- Herança e polimorfismo
- Interface `Persistencia<T>`
- Exceções personalizadas
- Banco de dados SQLite
- Relatórios em console

---

## Como Executar

### 1. Clonar o repositório

git clone https://github.com/Artur-Peres/Coworking-system


cd CoWorking-system


### 2. Dependência do SQLite
O projeto utiliza o driver:

org.xerial:sqlite-jdbc


### 3. Executar o programa
Via IDE ou terminal:

java -jar coworking_Projeto_jar


---

## Estrutura do Banco
Ao iniciar o programa, as seguintes tabelas são criadas automaticamente:

- espaços
- reservas
- pagamentos

O banco SQLite é armazenado no arquivo:

database.db


---

## Autores
- Nome(s) Artur Peres dos Santos, Bianca Mirelle frança galdino, Jonatham Alves Araujo
- Curso: ADS – IFPB
- Período: 2025.2


---

## Licença
Projeto desenvolvido para fins acadêmicos. Licença MIT opcional.
