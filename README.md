# Sistema em Java fazendo CRUD no MongoDB

Este sistema é um projeto de gerenciamento de prontuários médicos utilizando o banco de dados NoSQL MongoDB. Ele abrange entidades como Pacientes, Hospitais, Médicos, Especialidades, Endereços e Histórico (prontuário médico).

O sistema é implementado em Java, utilizando uma interface de console, e oferece funcionalidades de inserção, atualização, consulta e exclusão para todas as entidades.

## Requisitos

- Java 11 ou superior
- MongoDB instalado e em execução localmente ou em um servidor
- [VS Code](https://code.visualstudio.com/download) ou outra IDE com suporte para Java
- Em caso de [VS Code](https://code.visualstudio.com/download) instalar as extensões instaladas:
  - Extension Pack for Java

## Configuração do Banco de Dados

Certifique-se de que o MongoDB está instalado e em execução
- No Linux:
  ```shell
  sudo systemctl start mongod
  ```
- No Windows, inicie o serviço `MongoDB`.

- Configure as credenciais e o banco de dados na classe `DatabaseConfig`:


```java
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConfig {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "database";

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        return client.getDatabase(DATABASE_NAME);
    }
}

```

## Organização do Projeto
    
* [colecoes](back-end/colecoes): Contém arquivo com script para criar as coleções no MongoDB e insere registros nas mesma.
* [conexion](back-end/src/main/java/conexion): Contém a classe DatabaseConfig, responsável por configurar a conexão com o MongoDB.
* [controller](back-end/src/main/java/controller): Classes responsáveis pelo gerenciamento das entidades, como Pacientes, Hospitais, Médicos, etc.
* [model](back-end/src/main/java/model): Contém as classes de modelo para as entidades do sistema, como Paciente, Hospital, Medico, entre outras
* [repository](back-end/src/main/java/Repository):Classes responsáveis pela interação com o MongoDB para realizar as operações CRUD.
* [utils](back-end/src/main/java/utils): Contém utilitários, como a classe RemoverDependencia, que gerencia as exclusões relacionadas às dependências entre entidades.
* [principal](back-end/src/main/java/principal): A classe principal que executa o sistema e oferece a interface de console para o usuário.
    * Dentro dessa pasta também contém o [LinkVideo](back-end/src/main/java/principal/linkVideo.txt) com um link de um video no youtube com a explicação passo a passo de como execulta o projeto o teste de utilização do sistema.

## Funcionamento

- O sistema utiliza um modelo de repositório para interagir com o MongoDB.
- As entidades possuem relacionamentos por meio de IDs armazenados como `ObjectId` no MongoDB.
- O sistema oferece menus para gerenciar cada entidade, incluindo:
  - Pacientes: Cadastro, atualização, exclusão e consulta.
  - Hospitais: Cadastro, atualização, exclusão e consulta.
  - Histórico Médico: Gerenciamento completo de prontuários médicos, com associações entre Pacientes, Hospitais, Médicos e Especialidades.

## Instalando MongoDB no Linux (Debian/Ubuntu)

- Atualize os pacotes do sistema:

```shell
sudo apt update && sudo apt upgrade -y
```
- Instale o MongoDB:
```shell
sudo apt install mongodb -y
```
Inicie o serviço do MongoDB:
```shell
sudo systemctl start mongod
```
- Verifique o status do serviço:
```shell
sudo systemctl status mongod
```

## Configurando o Kit de Desenvolvedor Java (JDK)

- Instale o OpenJDK:
```shell
sudo apt install default-jdk -y
```
- Verifique a instalação do Java:
```shell
java -version
```
- Configure variáveis de ambiente. Adicione os caminhos Java ao arquivo .bash_profile para que fiquem disponíveis globalmente.
```shell
sudo systemctl status mysql
```
- Edite o arquivo `.bash_profile` (ou `.bashrc`) e adicione as linhas:
```shell
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
export PATH=$PATH:$JAVA_HOME/bin
```
- Aplique as alterações no terminal:
```shell
source ~/.bash_profile
```

## Contato
- [LinkedIn](https://linkedin.com/in/maurilio-marques)

