#!/usr/bin/bash
curl -H "Authorization: Bearer `curl -s sampleClientId:secret@localhost:8081/oauth/token -d "grant_type=client_credentials" | jq -r ".access_token"`"  localhost:8080/resource/test
