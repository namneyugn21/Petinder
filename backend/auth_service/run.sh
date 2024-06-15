#!/bin/sh

filename='./env/local.env'
source $filename
docker compose up -d
./gradlew bootRun