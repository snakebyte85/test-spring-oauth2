#!/usr/bin/bash

OUTPUT=`curl -s client1:secret@localhost:8081/oauth/token -d "grant_type=client_credentials"`

ACCESS_TOKEN=`echo "$OUTPUT" | jq -r ".access_token"`

echo "Output is: $OUTPUT"

echo "Access Token is: $ACCESS_TOKEN"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test
