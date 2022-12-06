TOPIC=$1; shift

docker exec -it kafka-join /opt/kafka/bin/kafka-topics.sh --zookeeper zookeeper:2181 \
--delete --topic $TOPIC