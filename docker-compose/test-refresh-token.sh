#!/usr/bin/bash

echo "*******************"
echo "FIRST CALL TO GET REFRESH TOKEN"

OUTPUT=`curl -s client2:secret@localhost:8081/oauth/token -d "grant_type=password&username=user&password=password"`

REFRESH_TOKEN=`echo "$OUTPUT" | jq -r ".refresh_token"`
ACCESS_TOKEN=`echo "$OUTPUT" | jq -r ".access_token"`

echo "Output is: $OUTPUT"

echo "Refresh Token is: $REFRESH_TOKEN"

echo "Access Token is: $ACCESS_TOKEN"

read -p "Press enter to continue"

echo "*******************"
echo "SECOND CALL TO GET ACCESS TOKEN"

OUTPUT=`curl -s client2:secret@localhost:8081/oauth/token -d "grant_type=refresh_token&refresh_token=$REFRESH_TOKEN"`

echo "Output is: $OUTPUT"

ACCESS_TOKEN=`echo "$OUTPUT" | jq -r ".access_token"`

echo "Access Token is: $ACCESS_TOKEN"

echo "*******************"
echo "FINAL CALL TO GET RESOURCE"

curl -H "Authorization: Bearer $ACCESS_TOKEN"  localhost:8080/resource/test
