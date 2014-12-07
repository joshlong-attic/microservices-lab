#!/bin/bash
set -e
at="`./oauth.sh $1 $2`"
curl http://localhost:8060/photo -H "Authorization: Bearer $at" 
