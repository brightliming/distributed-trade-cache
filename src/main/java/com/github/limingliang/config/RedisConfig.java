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

import com.github.limingliang.entity.Trade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 */
@Configuration
public class RedisConfig {
		@Bean("tradeRedisTemplate")
	    public RedisTemplate<String, Trade> redisTemplate(
	            RedisConnectionFactory redisConnectionFactory) {
	        RedisTemplate<String, Trade> template = new RedisTemplate<>();
	        //使用fastjson序列化
	        Jackson2JsonRedisSerializer<Trade> fastJsonRedisSerializer = new Jackson2JsonRedisSerializer<Trade>(Trade.class);
	        // value值的序列化采用fastJsonRedisSerializer
	        template.setValueSerializer(fastJsonRedisSerializer);
	        template.setHashValueSerializer(fastJsonRedisSerializer);
	        // key的序列化采用StringRedisSerializer
	        template.setKeySerializer(new StringRedisSerializer());
	        template.setHashKeySerializer(new StringRedisSerializer());
	 
	        template.setConnectionFactory(redisConnectionFactory);
	        return template;
	    }
		
}
