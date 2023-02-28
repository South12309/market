package ru.gb.market.carts.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.gb.market.carts.models.Cart;

import java.util.Map;

@Aspect
@Component
public class CartServiceProxy {

    private boolean isUpdateCartInRedis = false;
    private Map<String, Cart> proxyCart;

    @Around("execution(public * ru.gb.market.carts.services.CartService.getCurrentCart(*))")
    public Cart getCurrentCartFromProxy(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        String uuid = args[0].toString();
        if (!isUpdateCartInRedis) {
            Cart cartFromProxy = proxyCart.get(uuid);
            if (cartFromProxy == null) {
                Object proceed = proceedingJoinPoint.proceed();
                Cart cartFromRedis = (Cart) proceed;
                proxyCart.put(uuid, cartFromRedis);
                return cartFromRedis;
            } else {
                return cartFromProxy;
            }
        } else {
            Object proceed = proceedingJoinPoint.proceed();
            Cart currentCart = (Cart) proceed;
            proxyCart.put(uuid, currentCart);
            return currentCart;

        }
    }
}
