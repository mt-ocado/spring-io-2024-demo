# Spring I/O 2024 Demo

This project is a demo for Spring I/O 2024 conference.  
It is a simple Spring Boot application that configures Spring Cloud AWS `@SqsListener` annotation to receive the
messages from AWS SQS.
AWS is emulated locally by Localstack.

## Pre-requisites

- Docker
- Intellij IDEA

## Run the infrastructure

Run the following command in the command line to run Localstack. This will also create the required resources using AWS
CloudFormation.

```shell
docker compose up
```

## Run the application

The application can be run using IDEA run configurations.

## Test

Run the following command in the project root directory to send a test message to the SQS queue.

```shell
./scripts/send-message.sh 
```

Check the logs of the application to see the message received and processed correctly.