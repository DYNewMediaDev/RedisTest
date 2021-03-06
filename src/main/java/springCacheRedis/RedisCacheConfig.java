package springCacheRedis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * <p>
 *     spring cache和redis的结合配置类
 * </p>
 *
 * @author wangguangdong
 * @version 1.0
 * @Date 16/2/25
 * @see RedisCacheManager
 * @see JedisConnectionFactory
 * @see RedisTemplate
 * @see KeyGenerator
 */
@Configuration
@EnableCaching
@ComponentScan("springCacheRedis")
@PropertySource("classpath:/redis.properties")
public class RedisCacheConfig extends CachingConfigurerSupport{


    private static final ResourceBundle bundle = ResourceBundle.getBundle("redis");

    private String redisHost = bundle.getString("redis.ip");
    private int redisPort = Integer.valueOf(bundle.getString("redis.port"));
    private String redisPassword = bundle.getString("redis.auth");
    private int redisMaxWait = Integer.valueOf(bundle.getString("redis.pool.maxWait"));

    /*@Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        // Defaults
        redisConnectionFactory.setHostName(redisHost);
        redisConnectionFactory.setPort(redisPort);
        redisConnectionFactory.setPassword(redisPassword);
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    *//**
     * 缓存管理器
     * @param redisTemplate
     * @return
     *//*
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(redisMaxWait);// Sets the default expire time (in seconds)
        return cacheManager;
    }

    *//**
     * @description 自定义的缓存key的生成策略</br>
     *              若想使用这个key</br>
     *              只需要讲注解上keyGenerator的值设置为customKeyGenerator即可</br>
     * @return 自定义策略生成的key
     *//*
    @Bean
      public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
          @Override
          public Object generate(Object o, Method method, Object... objects) {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object obj : objects) {
              sb.append(obj.toString());
            }
            return sb.toString();
          }
        };
    }*/
}
