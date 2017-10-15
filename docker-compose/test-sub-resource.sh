#!/usr/bin/bash

OUTPUT=`curl -s client2:secret@localhost:8081/oauth/token -d "grant_type=password&username=user&password=password"`
ACCESS_TOKEN=`echo "$OUTPUT" | jq -r ".access_token"`

echo "Output is: $OUTPUT"

echo "Access Token is: $ACCESS_TOKEN"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/sub-test

