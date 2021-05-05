package com.example.kafka

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class BookStream(val config: KafkaConfig) {

	@PostConstruct
	fun startStream() {
		println("Bootstrap servers: " + config.bootstrapServers)
		val streamsConfiguration = Properties()
		streamsConfiguration[StreamsConfig.APPLICATION_ID_CONFIG] = "book-stream"
		streamsConfiguration[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = config.bootstrapServers
		streamsConfiguration[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.String().javaClass
		streamsConfiguration[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass

		val builder = StreamsBuilder()
		builder.stream(KafkaConfig.TOPIC_NAME, Consumed.with(Serdes.String(), Serdes.String()))
				.peek { key, value -> println("value: $value") }

		val stream = KafkaStreams(builder.build(), streamsConfiguration)
		stream.start()
	}
}