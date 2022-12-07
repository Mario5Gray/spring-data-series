cat ../data/polls.json | jq --raw-output .key,.value | while read POLLKEY; do
read POLLNAME

JSON='{"choiceId":"'${POLLKEY}'","text":"'${POLLNAME}'"}'
echo $POLLKEY : $JSON

curl -X POST http://localhost:9091/polls -H "kafka_messagekey: ${POLLKEY}" -H 'Content-Type: application/json' -H 'Accept: application/json' -d ${JSON}
done
