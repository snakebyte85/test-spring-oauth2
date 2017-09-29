#!/usr/bin/bash
curl -H "Authorization: Bearer `curl -s clientIdPassword:secret@localhost:8081/oauth/token -d "grant_type=password&username=user&password=password" | jq -r ".access_token"`"  localhost:8080/resource/test
