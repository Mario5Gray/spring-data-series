export TOPIC=$1; shift
export SER1=$1; shift
export SER2=$1; shift


docker exec -it broker /bin/kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic $TOPIC --key-deserializer org.apache.kafka.common.serialization.${SER1}Deserializer --value-deserializer org.apache.kafka.common.serialization.${SER2}Deserializer --property print.key=true --property key.separator="-" $@
