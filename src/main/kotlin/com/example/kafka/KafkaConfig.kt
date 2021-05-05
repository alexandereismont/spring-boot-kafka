package com.example.kafka

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

	@Value("\${kafka.bootstrap.servers}")
	lateinit var bootstrapServers: String

	companion object {
		val TOPIC_NAME = "book-repository"
	}
}