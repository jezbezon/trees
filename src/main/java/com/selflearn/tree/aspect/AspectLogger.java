package com.selflearn.tree.aspect;

import com.selflearn.tree.exceptions.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Component
@Slf4j
public class AspectLogger {

//    @After("execution(* com.selflearn.tree.Impl.CategoryServiceImpl.createCategory(CategoryDTO categoryDTO)")
//    public void logBefore() {
//        System.out.println("Logging before method execution.");
//        log.error("This category already exit!");
//    }

    //Printing log in console if there any error
    @AfterThrowing(
            pointcut = "execution(* com.selflearn.tree.service.CategoryService.createCategory(..))",
            throwing = "ex")
    public void logAfterThrow(Exception ex) {
        if(ex instanceof CustomizeException.BadRequestException) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:sss");
            log.error("This category already exit!  " + format.format(new Date()));
        }
    }

//    @Around("execution(* com.selflearn.tree.service.*.*(..))")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//
//        if (args != null && args.length > 0) {
//            List<Object> list = new ArrayList<>();
//            for (var a : args) {
//                System.out.println("around : before execution : args = " + a);
//                if (a instanceof String val) {
//                    list.add("Task name(%s - %s)".formatted(val, Instant.now().toEpochMilli()));
//                } else {
//                    list.add(a);
//                }
//            }
//            args = list.toArray();
//        }
//
//        Object result = joinPoint.proceed(args);
//
//        System.out.println("around : after execution : result = " + result);
//
//        return result;
//    }
}
