📌 Projeto Alerta Natural

O Alerta Natural é uma plataforma inovadora para o acompanhamento de desastres naturais, como enchentes e tempestades. Além de fornecer previsões meteorológicas em tempo real, o sistema conta com uma rede social integrada, permitindo que os usuários compartilhem fotos e vídeos das ocorrências em suas regiões.

🚀 Tecnologias Utilizadas

* Java / Spring Boot /Spring Seurity  
* Banco de Dados: MySQL  
* JPA / Hibernate  
* Swagger (para documentação de API)  
* Google OAuth Client Library  

📦 Estrutura do Projeto

/projeto-Alerta-Natural-Backend  
│── src/  
│   ├── main/  
│   │   ├── java/com.br.alertanatural/  
│   │   │   ├── Config/  
│   │   │   ├── controllers/  
│   │   │   ├── DTOs/  
│   │   │   ├── models/  
│   │   │   ├── repositories/  
│   │   │   ├── services/  
│   │   │   ├── util/  
│── resources/  
│   ├── application.properties  
│── pom.xml  
│── README.md  

📖 Como Rodar o Projeto  

🖥️ Pré-requisitos  
* Antes de começar, certifique-se de ter instalado:
* Uma IDE (Intellij de preferencia)
* Java 17+
* MySQL

🔧 Configuração do Ambiente  

### Clone o repositório:
```
 git clone https://github.com/kleberton-santos/alerta-natural.git
```
### Abra o Intellij:
* Menu / Open / Folder / Selecione a pasta do projeto

### Abra o Terminal direto no Intellij mesmo:
* Build do projeto:
```
 mvn clean install
```
📌 Funcionalidades Principais

🔴 Monitoramento de Desastres: Acompanhe eventos como enchentes e tempestades em tempo real.

🌦 Previsão do Tempo: Consulte as condições climáticas de sua região.

📸 Rede Social: Poste fotos e vídeos sobre ocorrências e interaja com outros usuários.

❤️ Interação Social: Curta e comente publicações de outros usuários.

🤝 Adição de Amigos: Conecte-se com outras pessoas para compartilhar informações.

📌 Endpoints Principais

