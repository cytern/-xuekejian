package com.xuexikuaile.deng.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: cytern
 * @Date: 2020/5/25 21:02
 */

/**
 * @apiNote 各种不同权限拦截器，且解决跨域问题
 */
@Configuration
@AllArgsConstructor
public class LoginInterceptor implements WebMvcConfigurer {
     @Autowired
    private AllInterceptor allInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;
    @Autowired
    private TeacherInterceptor teacherInterceptor;
    @Autowired
    private RootInterceptor rootInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(allInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                       //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
        InterceptorRegistration registration2 = registry.addInterceptor(studentInterceptor);
        registration2.addPathPatterns("/student/**");
        registration2.excludePathPatterns(

                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
        InterceptorRegistration registration3 = registry.addInterceptor(teacherInterceptor);
        registration3.addPathPatterns("/teacher/**");
        registration3.excludePathPatterns(

                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
        InterceptorRegistration registration4 = registry.addInterceptor(rootInterceptor);
        registration4.addPathPatterns("/root/**");
        registration4.excludePathPatterns(

                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css",             //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );

    }
}
