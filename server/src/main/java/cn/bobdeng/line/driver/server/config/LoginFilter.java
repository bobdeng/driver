package cn.bobdeng.line.driver.server.config;

import cn.bobdeng.line.userclient.UserClient;
import cn.bobdeng.line.userclient.UserDTO;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Setter
public class LoginFilter implements Filter {
    UserClient userClient;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("x-token");
        if(token==null){
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }else {
            try {
                UserDTO user = userClient.parseToken(token);
                request.setAttribute("user", user);
                filterChain.doFilter(servletRequest, servletResponse);
            }catch (Exception e){
                e.printStackTrace();
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
    }

    @Override
    public void destroy() {

    }
}
