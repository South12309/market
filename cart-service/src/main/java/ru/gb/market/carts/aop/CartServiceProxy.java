package ru.gb.market.carts.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.gb.market.carts.models.Cart;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class CartServiceProxy {

    private final Map<String, Cart> proxyCart=new HashMap<>();

//    @Around("execution(public void org.springframework.data.redis.core.ValueOperations.set(..))")
//    public void setCartInRedis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Object[] args = proceedingJoinPoint.getArgs();
//        proxyCart.put((String)args[0], (Cart)args[1]);
//        proceedingJoinPoint.proceed();
//        log.info("CARTNUMBER = " + args[0].toString() + "CART = " + args[1].toString());
//    }

    @Around("execution(public * ru.gb.market.carts.aop.RedisTemplateAdapter.get(*))")
    public Cart getCurrentCartFromProxy(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        log.info("Получил аргумент " + args.toString());
        String uuid = args[0].toString();
        log.info("GETTING UUID " + uuid);
        Cart cartFromProxy = proxyCart.get(uuid);
        log.info("GETTING CART FROM PROXY " + ((cartFromProxy!=null) ? cartFromProxy.toString() : "null"));
        if (cartFromProxy == null) {
            Object proceed = proceedingJoinPoint.proceed();
            Cart cartFromRedis = (Cart) proceed;
            log.info("GETTING CART FROM REDIS " + cartFromRedis.toString());
            proxyCart.put(uuid, cartFromRedis);
            return cartFromRedis;
        } else {
            return cartFromProxy;
        }
    }



//    @After("execution(public void ru.gb.market.carts.services.CartService.saveCartToRedis(..))")
//    public void setCartInRedis(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        proxyCart.put((String)args[0], (Cart)args[1]);
//        log.info("CARTNUMBER = " + args[0].toString() + "CART = " + args[1].toString());
//    }
    @After("execution(public void ru.gb.market.carts.aop.RedisTemplateAdapter.set(..))")
    public void setCartInRedis(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        proxyCart.put((String)args[0], (Cart)args[1]);
        log.info("CARTNUMBER = " + args[0].toString() + "CART = " + args[1].toString());
    }
}
