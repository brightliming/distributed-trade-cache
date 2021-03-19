/**  
 * All rights Reserved, Designed By www.troila.com
 * @Title:  RedisConfig.java   
 * @Package com.troila.config   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 卓朗科技_limingliang     
 * @date:   2018年8月10日 下午6:19:11   
 * @version V1.0 
 * @Copyright: 2018 www.troila.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津卓朗科技信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.github.limingliang.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.limingliang.entity.Trade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 */
@Configuration
public class RedisConfig {
		@Bean(name = "redisTemplate")
		public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory factory) {
			RedisTemplate<String, Object> template = new RedisTemplate<>();
			template.setConnectionFactory(factory);
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
			mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(mapper);

			StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
			template.setKeySerializer(stringRedisSerializer);
			template.setHashKeySerializer(stringRedisSerializer);
			template.setValueSerializer(jackson2JsonRedisSerializer);
			template.setHashValueSerializer(jackson2JsonRedisSerializer);
			template.afterPropertiesSet();

			return template;
		}
		
}
