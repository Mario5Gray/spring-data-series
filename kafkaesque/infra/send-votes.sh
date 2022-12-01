cat ../data/votes.json | jq --raw-output .voter,.choiceId | while read VOTEKEY; do
read value
 
curl -vvv -X POST http://localhost:9090/vote -H "kafka_messagekey: $VOTEKEY" -H 'Content-Type: application/json' -H 'Accept: application/json'  -d $value
done
