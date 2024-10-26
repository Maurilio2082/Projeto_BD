# Sistema em Java fazendo CRUD no MySQL

Esse sistema é composto por um conjunto de tabelas que representam prontuário médico, contendo tabelas como: pacientes, hospitais, médicos, especialidades, endereços e histórico (prontuário).

O sistema exige que as tabelas existam, então basta executar o script [SQL CREATE](back-end/sql/create_database.sql) para criação das tabelas e preenchimento dos dados com o [SQL INSERT](back-end/sql/insert_database.sql).

O código é executado através do método main do Java, rodando em console. Entretanto, a estrutura do projeto foi desenvolvida para Spring Boot, possibilitando uma escalabilidade para desenvolver uma interface no futuro.

Para executar o sistema, é necessário ter o [VS Code](https://code.visualstudio.com/download) instalado, com as seguintes extensões instaladas:
 - Extension Pack for Java

Na classe DatabaseConfig, dentro do módulo conexão, deve-se adicionar o nome da database e um usuário e senha com permissão para usar a base:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/nomeDatabase";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

## Organização
- [diagramas](back-end/diagramas): Nesse diretório está o [diagrama relacional](back-end/diagramas/DIAGRAMA_RELACIONAL_PRONTUARIO.pdf) (lógico) do sistema.
    * O sistema possui oito entidades: ENDERECO, PACIENTE, HOSPITAL, MEDICO, ESPECIALIDADE, HISTORICO e as tabelas de relacionamento MEDICO_ESPECIALIDADE e MEDICO_HOSPITAL.
- [sql](back-end/sql): Nesse diretório estão os scripts para criação das tabelas e inserção de dados fictícios para testes do sistema.
    * Certifique-se de que o usuário do banco possui todos os privilégios antes de executar os scripts de criação. Caso ocorra erro, execute o comando a seguir com o superusuário via MySQL: `GRANT ALL PRIVILEGES ON NOMEDABASE.* TO 'usuario'@'localhost'; FLUSH PRIVILEGES;`
    * [create_database.sql](back-end/sql/create_database.sql): script responsável pela criação das tabelas, relacionamentos e criação de permissão no esquema LabDatabase.
    * [insert_database.sql](back-end/sql/insert_database.sql): script responsável pela inserção dos registros fictícios para testes do sistema.
- [src/main/java](back-end/src/main/java): Nesse diretório estão os scripts do sistema.
    * [conexion](back-end/src/main/java/conexion): Nesse repositório encontra-se o [módulo de conexão com o banco de dados MySQL](back-end/src/main/java/conexion/DatabaseConfig.java). Esse módulo possui a configuração para conexão no banco de dados.
    * [controller](back-end/src/main/java/controller): Nesse diretório encontram-se as classes controladoras, responsáveis por realizar inserção, alteração e exclusão dos registros das tabelas.
    * [model](back-end/src/main/java/model): Nesse diretório encontram-se as classes das entidades descritas no [diagrama relacional](back-end/diagramas/DIAGRAMA_RELACIONAL_PRONTUARIO.pdf).
    * [reports](back-end/src/main/java/reports): Nesse diretório encontra-se a [Relatórios](back-end/src/main/java/reports/Relatorios.java) responsável por gerar todos os relatórios do sistema e [ImprimirRelatorios](back-end/src/main/java/reports/ImprimirRelatorios.java), responsável pela exibição de todos os relatórios.
    * [sql](back-end/src/main/java/sql): Nesse diretório encontram-se os scripts utilizados para geração dos relatórios a partir da [classe relatórios](back-end/src/main/java/reports/Relatorios.java).
    * [utils](back-end/src/main/java/utils): Nesse diretório encontram-se scripts de [configuração](back-end/src/main/java/utils/Config.java) e automatização da [tela de informações iniciais](back-end/src/main/java/utils/SplashScreen.java).
    * [principal](back-end/src/main/java/principal): Script responsável por ser a interface entre o usuário e os módulos de acesso ao banco de dados. Deve ser executado após a criação das tabelas.
        * Dentro dessa pasta também contém o txt [apresentacao] com um link de um video no youtube com a explicação passo a passo de utilização do sistema.
- Outros arquivos, como pom.xml e os wvnw, são de configuração do projeto Spring Boot.

### Instalando MySQL e MySQL Workbench no Linux (Debian/Ubuntu)
- Para instalar o MySQL e o MySQL Workbench, siga os passos abaixo:
**Atualize o sistema** para garantir que você tem a última versão dos pacotes:
```shell
sudo apt update && sudo apt upgrade -y
```
- Instale o MySQL Server:
```shell
sudo apt install mysql-server -y
```
- Execute a configuração inicial do MySQL. Este comando iniciará um assistente para definir a senha root e outras configurações básicas de segurança:
```shell
sudo mysql_secure_installation
```
- Instale o MySQL Workbench:
```shell
sudo apt install mysql-workbench -y
```
- Verifique o status do MySQL para garantir que está ativo:
```shell
sudo systemctl status mysql
```

### Configurando o Kit de Desenvolvedor Java (JDK)

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
- [E-Mail](mailto:mauriliomg8@gmail.com)
