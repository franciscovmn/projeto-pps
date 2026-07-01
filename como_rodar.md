# Como Colocar o Projeto em Funcionamento

## Pré-requisitos
ambiente de desenvolvimento:
* **Java Development Kit (JDK):** Versão 11 ou superior (recomendado JDK 17 ou posterior).
* **IDE de sua preferência:** IntelliJ IDEA, ou VS Code .
* **Git:** Para clonagem e controle de versão do repositório.

---

## Passos para Configuração e Execução

### 1. Clonar o Repositório
Abra o seu terminal e execute o comando abaixo para clonar o projeto para a sua máquina local:

```bash
git clone https://github.com/franciscovmn/projeto-pps
cd projeto-pps
```

### 2. Preparação da Massa de Dados (CSV)

O sistema conta com um módulo de carga de dados automatizada (`CsvLoader`).

* Certifique-se de que os arquivos de dados `.csv` necessários para o povoamento estejam localizados no diretório `src/main/resources/data` do projeto, para que os repositórios carreguem as informações de usuários e áreas corretamente.

### 3. Compilação e Execução

O projeto utiliza **Maven** para gerenciamento de dependências e compilação.

#### Opção 1 – Utilizando o Maven Wrapper (Recomendado)

> **Recomendado para Windows**, pois **não é necessário ter o Maven instalado**. Basta possuir o JDK instalado.

Abra o Prompt de Comando (CMD) ou PowerShell na pasta raiz do projeto e execute:

```bat
mvnw.cmd clean compile
```

Após a compilação, execute a classe principal pela IDE (IntelliJ IDEA ou VS Code) ou utilize o comando correspondente caso o projeto esteja configurado para execução via Maven.

---

#### Opção 2 – Utilizando o Maven Instalado

Caso o Maven já esteja instalado e configurado na variável de ambiente `PATH`, execute:

```bat
mvn clean compile
```

---

#### Execução pela IDE

1. Abra a pasta raiz do projeto na IDE (IntelliJ IDEA ou VS Code).
2. Aguarde o Maven baixar automaticamente todas as dependências do projeto.
3. Localize a classe `Main.java`.
4. Execute a classe `Main.java`.