package ru.gb.market.carts.aop;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.gb.market.carts.models.Cart;

@Component
@AllArgsConstructor
public class RedisTemplateAdapter<K, V> {
    private final RedisTemplate<K, V> redisTemplate;

    public Boolean hasKey(K uuid) {
        return redisTemplate.hasKey(uuid);

    }

    public void set(K uuid, V obj) {
        redisTemplate.opsForValue().set(uuid, obj);
    }
    public Object get(K uuid) {
      return  redisTemplate.opsForValue().get(uuid);
    }


}
