package com.nttdata.movementwallet.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.nttdata.movementwallet.entity.MovementWallet;
@Configuration
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public class RedisConfig {
	@Bean
	public ReactiveHashOperations<String, Integer, MovementWallet> hashOperations(
			ReactiveRedisConnectionFactory redisConnectionFactory) {
		var template = new ReactiveRedisTemplate<>(redisConnectionFactory,
				RedisSerializationContext.<String, MovementWallet>newSerializationContext(new StringRedisSerializer())
						.hashKey(new GenericToStringSerializer<>(Integer.class))
						.hashValue(new Jackson2JsonRedisSerializer<>(MovementWallet.class)).build());
		return template.opsForHash();
	}
}
