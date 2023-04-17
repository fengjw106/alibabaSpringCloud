package org.example.cloud.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@Component
public class MyLogGateWayFilter implements GlobalFilter, OrderedFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("time:" + new Date() + "\t 执行了自定义的全局过滤器: " + "MyLogGateWayFilter" + "hello");
        // String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        // if (uname == null) {
        //     System.out.println("****用户名为null，无法登录");
        //     exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
        //     return exchange.getResponse().setComplete();
        // }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
