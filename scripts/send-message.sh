awslocal sqs send-message \
  --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue \
  --message-body 'Spring I/O 2024' \
  --message-attributes '{"tenantId": {"DataType": "String", "StringValue": "otp-tenant-id"}}'