#!/usr/bin/bash

OUTPUT=`curl -s client3:@localhost:8081/oauth/token -d "grant_type=authorization_code&code=$1"`
ACCESS_TOKEN=`echo "$OUTPUT" | jq -r ".access_token"`

echo "Output is: $OUTPUT"

echo "Access Token is: $ACCESS_TOKEN"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test

read -p "Press enter to continue"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test

read -p "Press enter to continue"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test

read -p "Press enter to continue"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test
