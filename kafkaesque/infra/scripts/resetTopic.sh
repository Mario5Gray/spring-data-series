TOPIC=$1; shift
GROUP=$1; shift

docker exec -it broker /bin/kafka-consumer-groups --bootstrap-server broker:9092 \
--topic "$TOPIC" --group "$GROUP" --reset-offsets --to-earliest --execute
