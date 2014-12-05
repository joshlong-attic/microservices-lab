at=`curl -X POST -u acme:acmesecret http://localhost:8002/auth/oauth/token -H "Accept: application/json" -d "password=$2&username=$1&grant_type=password&scope=read&client_secret=acmesecret&client_id=acme" |  cut -d\" -f4 `
echo $at
