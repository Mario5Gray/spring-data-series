TOPIC=$1; shift

docker exec -it broker /bin/kafka-topics --bootstrap-server broker:9092 \
--delete --topic $TOPIC
