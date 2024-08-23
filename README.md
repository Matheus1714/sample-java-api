![alt text](./.github/imgs/banner.png)

# Sample Java API

Um exemplo de API feito com `java` puro para aprendizado pessoal.

Nesse repositório não fui utilizado nenhuma dependências ou framework externo.

## Tecnologias

- `Java (openjdk 17.0.4)`

## Execução do Projeto

Compilando arquivos necessários

```bash
javac -d out src/Main.java src/controllers/UserController.java src/models/UserModel.java src/views/UserView.java
```

Executando arquivo de saída

```bash
java -cp out Main
```

## Requisições

**get-users**

```bash
GET http://localhost:8000/users
```


**create-user**

```bash
POST http://localhost:8000/users
Content-Type: application/json

{
    "name": "Matheus",
    "email": "matheus@test.com"
}
```