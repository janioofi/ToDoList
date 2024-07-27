## Sobre
Este projeto foi desenvolvido utilizando o framework Spring Boot com o objetivo de criar uma API RESTful para gerenciar tarefas. A seguir, destacam-se os principais aspectos do desenvolvimento:

### Video de como testar a aplicação
Link: https://drive.google.com/file/d/1MqqattIVLg8gVwbWaDPqto6KkyVzQwxj/view?usp=sharing

#### Estrutura do projeto
- Camadas: O projeto segue a arquitetura em camadas, separando as responsabilidades em controladores (controllers), serviços (services), repositórios (repositories) e entidades de domínio (domain entities).
- Pacotes: O código está organizado em pacotes bem definidos para facilitar a manutenção e a escalabilidade.

### Tecnologia utilizada
- Spring Boot
- Maven
- Spring Data
- Spring Security
- PostgreSQL
- JWT
- Java 17

### Segurança da aplicação
Para ter acesso a aplicação, necessário efetuar o registro e depois o login, após isso você terá acesso a todos os endpoints da aplicação, mas existem regras na aplicação
- Todas as tarefas, tem um vinculo com o usuário que a criou.
- Todos os usuários conseguem verificar as tarefas do sistema, mas APENAS, o usuário que a criou pode fazer uma altereção, como PUT ou DELETE. Caso você tente alterar ou apagar uma tarefa que não seja vinculada a você, será apresetado um erro.
- O mesmo vale para os usuários, a pessoa que está logada, consegue verficar todos os usuários do sistema, junto com suas informações, mas caso ele queira apagar ou atualizar alguma informação, não irá conseguir, pois apenas o usuário pode fazer alguma alteração no seu cadastro.

#### Configurações de Segurança

- Foi configurado o Spring Security para proteger as APIs, exigindo autenticação para acesso aos endpoints.
- A configuração de segurança está na classe SecurityConfiguration.

#### Gerenciamento de Dependências:
- O Maven foi utilizado para gerenciar as dependências do projeto. Todas as dependências necessárias estão listadas no arquivo pom.xml.

#### Deploy
```bash
https://janioofi.up.railway.app/
```
#### Documentação
```bash
https://janioofi.up.railway.app/swagger-ui/index.html
```

## Como utilizar
- Necessário realizar um registro de usuário para ter acesso aos endpoints.
```bash
Método: POST
URL: https://janioofi.up.railway.app/api/register

{
   "usuario":"admin",
   "senha":"admin"
}
```
- Fazer o login preenchendo com as mesmas informações registradas e pegar o token que será gerado como resposta para acessar os endpoints:
```bash
Método: POST
URL: https://janioofi.up.railway.app/api/login

{
   "usuario":"admin",
   "senha":"admin"
}
```
- Após a realização do login, caso for conclúdido com sucesso, irá retornar um código token, é necessário guardar este token para acessar os endpoints da aplicação.
- Testar utilizando o próprio swagger, no cabeçalho, tem um campo "Authorizate", basta acessar, colocar o token no "Value", e dar um "Authorize".
- Para criar uma tarefa, utilize está sintaxe:
```bash
Método: POST
URl: https://janioofi.up.railway.app/api/v1/tarefas

{
  "descricao": "Andar com os cães",
  "status": "PENDENTE"
}

```
- Existe três status, o padrão é PENDENTE, depois vem o CONCLUIDO e por último CANCELADO.
- Para atualizar uma tarefa, basta preencher o body com as novas informações e na URL, onde está {id}, adicionar o id da tarefa que deseja atualizar.
```bash
Método: PUT 
URL: https://janioofi.up.railway.app/api/v1/tarefas/{id}

{
  "descricao": "Andar com os cães",
  "status": "CONCLUIDO"
}
```
- Para listar todas as tarefas do sistema:
```bash
Método: GET
URL: https://janioofi.up.railway.app/api/v1/tarefas
```
- Para retornar os dados do usuário e todas as tarefas dele, necessário adicionar o id do usuário na url:
```bash
Método: GET
URL: https://janioofi.up.railway.app/api/v1/usuarios/{id}
```
- Para remover uma tarefa, só inserir o ID da tarefa na URL:
```bash
Método: DELETE
URL: https://janioofi.up.railway.app/api/v1/tarefas/{id}
```



## Autor
- [@janioofi](https://www.instagram.com/janioofi/)
