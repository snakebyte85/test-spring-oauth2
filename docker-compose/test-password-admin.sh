#!/usr/bin/bash

ACCESS_TOKEN=`curl -s client2:secret@localhost:8081/oauth/token -d "grant_type=password&username=admin&password=password" | jq -r ".access_token"`

echo "Access Token is: $ACCESS_TOKEN"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test
