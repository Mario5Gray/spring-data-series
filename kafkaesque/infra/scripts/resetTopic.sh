TOPIC=$1; shift
GROUP=$1; shift

docker exec -it kafka-join /opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 \
--topic "$TOPIC" --group "$GROUP" --reset-offsets --to-earliest --execute