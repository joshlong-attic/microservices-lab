#!/bin/bash
set -e
at="`./oauth.sh $1 $2`"
curl http://localhost:8060/photo -F "multipartFile=@$3" -H "Authorization: Bearer $at"
