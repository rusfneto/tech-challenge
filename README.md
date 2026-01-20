Tech Challenge

API em Java (Maven) com suporte a Docker/Docker Compose e collection do Postman para testes.

Requisitos

Com Docker: Docker + Docker Compose
ou

Sem Docker: JDK + Maven (ou Maven Wrapper)

Rodando com Docker

docker compose up -d 
ou docker compose up -d --build

Parar:
docker compose down

Testes

Importe a collection em CollectionsPostman/ no Postman e ajuste a baseUrl conforme a porta da aplicação.

Estrutura

src/ código da aplicação

docker-compose.yml ambiente local

Dockerfile build da aplicação

CollectionsPostman/ collection para testes
