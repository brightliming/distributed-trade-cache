package com.github.limingliang.service;

import com.github.limingliang.entity.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisService
 * @Description REDIS服务类
 * @Author limingliang
 * @Date 2020/4/23 8:24
 * @Version 1.0
 **/
@Service
public class RedisService {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return Boolean
     */
    public Boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为 null
     * @return 时间(秒) 返回 0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 批量获取keys
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 批量获取
     * @param keys
     * @return
     */
    public List<Object> multiGet(Set<String> keys){
       return  redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public Boolean set(String key, Trade value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public Boolean set(String key, Trade value, Long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return Long
     */
    public Long incr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几
     * @return Long
     */
    public Long decr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * HashGet
     *
     * @param key  键 不能为 null
     * @param item 项 不能为 null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     *
     * @param key
     * @return
     */
    public Long hsize(String key){
        return redisTemplate.opsForHash().size(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public List<Object> hvalues(String key){
        return redisTemplate.opsForHash().values(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public Set<Object> hkeys(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取 hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 取 hashKey对应的所有键值
     * @param key
     * @param hashKeys
     * @return
     */
    public List<Object> hMultiGet(String key, Collection<Object> hashKeys){
       return  redisTemplate.opsForHash().multiGet(key,hashKeys);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public Boolean hmset(String key, Map<String, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为 null
     * @param item 项 可以使多个不能为 null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为 null
     * @param item 项 不能为 null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return Double
     */
    public Double hincr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return Double
     */
    public Double hdecr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 根据 key获取 Set中的所有值
     *
     * @param key 键
     * @return Set
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, Trade... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTime(String key, Long time, Trade... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return List
     */
    public List<Object> lGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public Object lGet(String key) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return Long
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；
     *              index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    public Object lGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean lSet(String key, Trade value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean lSet(String key, Trade value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return Boolean
     */
    public Boolean lSet(String key, List<Trade> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return Boolean
     */
    public Boolean lSet(String key, List<Trade> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return Boolean
     */
    public Boolean lUpdateIndex(String key, Long index, Trade value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, Long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向ZSET中添加元素
     * @param key 键值
     * @param value 元素
     * @param score 分值
     * @return
     */
    public Boolean zAdd(String key,Trade value,double score){
        try {
            return redisTemplate.opsForZSet().add(key,value,score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Object> reverseRangeByScore(String key,double min,double max,long offset,long count){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max,offset,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> reverseRangeByScore(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 分页存取数据
     * @param key  hash存取的key
     * @param hkey hash存取的hkey
     * @param score 指定字段排序
     * @param value
     * @return
     */
    public boolean setPage(String key, String hkey, double score, Object value){
        boolean result = false;
        try {
            result = redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public  Boolean execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    redisOperations.opsForZSet().add(key+":page", hkey, score);
                    redisOperations.opsForHash().put(key, hkey, value);
                    redisOperations.exec();
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 删除页记录
     * @param key
     * @param hkey
     * @return
     */
    public boolean delPage(String key,String hkey){
        boolean result = false;
        try {
            result = redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public  Boolean execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    redisOperations.opsForZSet().remove(key+":page", hkey);
                    redisOperations.opsForHash().delete(key, hkey);
                    redisOperations.exec();
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean delPageTable(String key){
        boolean result = false;
        try {
            result = redisTemplate.execute(new SessionCallback<Boolean>() {
                @Override
                public  Boolean execute(RedisOperations redisOperations) throws DataAccessException {
                    redisOperations.multi();
                    List<String> keys = Arrays.asList(key+":page",key);
                    redisOperations.delete(keys);
                    redisOperations.exec();
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 分页取出 hash中hkey值
     * @param key
     * @param offset
     * @param count
     * @return
     */
    public Set<Object> getPage(String key, int offset, int count){
        Set<Object> result = null;
        try {
            result = redisTemplate.opsForZSet().rangeByScore(key+":page", 0, 1000000, (offset-1)*count, count);//1 100000代表score的排序氛围值，即从1-100000的范围
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 获取hash中素有的hkey值
     * @param key
     * @return
     */
    public Set<Object> getPageAll(String key){
        Set<Object> result = null;
        try {
            result = redisTemplate.opsForZSet().range(key+":page", 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 计算key值对应的数量
     * @param key
     * @return
     */
    public Integer getSize(String key){
        Integer num = 0;
        try {
            Long size = redisTemplate.opsForZSet().zCard(key+":page");
            return size.intValue();
        } catch (Exception e) {
            e.printStackTrace();
            num =  0;
        }
        return num;
    }
}