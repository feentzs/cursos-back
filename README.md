"""# API de Gerenciamento de Cursos

API RESTful simplificada para o controlo de cursos, desenvolvida com **Spring Boot**, apresentando suporte para exclusão lógica e documentação automática.

---

## Instalação e Execução

1. **Requisitos:** Java 21+ e MariaDB/MySQL.
2. **Base de Dados:** Crie um esquema chamado `cursos_db`.
3. **Configuração:** Atualize as credenciais de acesso no ficheiro `src/main/resources/application.properties`.
4. **Execução:** Inicie a aplicação e aceda aos testes via Swagger:
   * 🔗 [Swagger UI (Documentação)](http://localhost:8080/swagger-ui/index.html)

---

## Endpoints (`/cursos`)

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| **GET** | `/` | Lista todos os cursos ativos. |
| **GET** | `/{id}` | Procura um curso específico por ID. |
| **POST** | `/` | Regista um novo curso no sistema. |
| **PUT** | `/` | Atualiza os dados de um curso existente. |
| **DELETE** | `/{id}` | Desativa um curso (Exclusão Lógica). |

### Estrutura do JSON (POST / PUT)
```json
{
  "nome": "Desenvolvimento Web Fullstack",
  "periodo": "NOTURNO" 
}