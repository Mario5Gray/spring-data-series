cat ../data/votes.json | while read line; do

curl -X POST http://localhost:9090/vote -H 'Content-Type: application/json' -H 'Accept: application/json'  -d $line
done
