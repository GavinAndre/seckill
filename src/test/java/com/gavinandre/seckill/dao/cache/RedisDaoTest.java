package com.gavinandre.seckill.dao.cache;

import com.gavinandre.seckill.dao.SeckillDao;
import com.gavinandre.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() {
        //get and put
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null) {
            seckill = seckillDao.queryById(id);
            if (seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

    /**
     * 单实例测试
     */
    @Test
    public void testJedisDemo1() {
        // 1.设置ip地址和端口
        Jedis jedis = new Jedis("192.168.1.110", 6379);
        jedis.auth("123456");
        // 2.保存数据
        jedis.set("name", "gavinandre");
        // 3.获取数据
        String value = jedis.get("name");
        System.out.println(value);
    }

    @Test
    public void testJedisDemo2() {
        //获取连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(30);
        //设置最大空闲连接数
        config.setMaxIdle(10);
        //获得连接池
        JedisPool jedisPool = new JedisPool(config, "192.168.1.110", 6379);
        //获得核心对象
        Jedis jedis = null;
        try {
            //通过连接池获得连接
            jedis = jedisPool.getResource();
            jedis.auth("123456");
            //设置数据
            jedis.set("name", "tixialu");
            //获取数据
            String name = jedis.get("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }

}