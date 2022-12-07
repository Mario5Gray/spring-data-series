cat ../data/votes.json | while read line; do
CHOICEID=`echo $line | jq --raw-output .choiceId`
echo $line
curl -X POST http://localhost:9090/vote -H 'Content-Type: application/json' -H 'Accept: application/json'  -H "messagekey: ${CHOICEID}" -d $line
done
