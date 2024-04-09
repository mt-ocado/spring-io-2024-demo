#!/usr/bin/env bash

awslocal cloudformation create-stack --stack-name my-stack --template-body file:///mounts/cf-template.yml