# Encurtador de URLs com AWS Services - Semana NLW Java com AWS

Este projeto é um **encurtador de URLs** desenvolvido em **Java puro**, utilizando o SDK da AWS e serviços como **S3**, **API Gateway** e **CloudWatch** para logs.

## **Funcionalidades**

- **Armazenamento de URLs**: Armazena URLs com data de expiração no S3 em formato JSON.
- **Geração de URLs Encurtadas**: Gera automaticamente uma URL encurtada ao receber eventos do S3.
- **Logging**: Registra operações no CloudWatch para monitoramento e depuração.
- **Integração com AWS**:
  - **S3**: Armazenamento para URLs originais com data de expiração da mesma.
  - **API Gateway**: Exposição dos endpoints HTTP para interação com a aplicação.
  - **CloudWatch**: Logs centralizados para observabilidade.

## **Tecnologias Utilizadas**

- **Linguagem**: Java (SDK da AWS)
- **Serviços AWS**:
  - **S3**: Armazenamento para URLs com data de expiração.
  - **API Gateway**: Exposição de endpoints HTTP para chamar funções lambda.
  - **Lambda**: Processamento de eventos do S3.
  - **CloudWatch**: Logs de operações e erros.
