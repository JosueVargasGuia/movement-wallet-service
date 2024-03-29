package com.nttdata.movementwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.nttdata.movementwallet.entity.MovementWallet;

@Configuration
public class RedisConfig {
	@Bean
	ReactiveRedisOperations<String, MovementWallet> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<MovementWallet> serializer = new Jackson2JsonRedisSerializer<>(
				MovementWallet.class);

		RedisSerializationContext.RedisSerializationContextBuilder<String, MovementWallet> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());

		RedisSerializationContext<String, MovementWallet> context = builder.value(serializer).build();

		return new ReactiveRedisTemplate<>(factory, context);
	}
}
