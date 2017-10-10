#!/usr/bin/bash

ACCESS_TOKEN=`curl -s sampleClientId:secret@localhost:8081/oauth/token -d "grant_type=client_credentials" | jq -r ".access_token"`

echo "Access Token is: $ACCESS_TOKEN"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test
