package com.example.polls.config;

import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class LoggingAspect {

    @Around(
            "execution(public * com.example.polls.controller..*(..)) || "
                    + "execution(public * com.example.polls.repository..*(..)) || "
                    + "execution(public * com.example.polls.config.SpringSecurityAuditAwareImpl.*(..)) || "
                    + "execution(public * com.example.polls.service..*(..)) ")
    public Object log(final ProceedingJoinPoint point) throws Throwable {
        Logger LOGGER = null;
        String methodName = null;
        Stopwatch timer = null;
        Object retVal = null;
        Object target = point.getTarget();
        try {
            if (target != null) {
                LOGGER = LoggerFactory.getLogger(target.getClass());
                timer = Stopwatch.createStarted();
                methodName = ((MethodSignature) point.getSignature()).getMethod().getName();

                Object[] args = point.getArgs();
                String params = "";
                if (args != null && args.length > 0) {
                    ArrayList<String> list = new ArrayList<>(args.length);
                    for (Object o : args) {
                        list.add(String.valueOf(o));
                    }
                    params = String.join(",",
                                         list);
                }

                LOGGER.info(String.format("ASPECT-start: %s(%s)",
                                          methodName,
                                          params));
            }

            retVal = point.proceed();
            return retVal;
        } catch (final Exception ex) {
            LOGGER.error("ASPECT-Exception caught in LoggingAspect - ",
                         ex);
            throw ex;
        } finally {
            if (target != null) {
                LOGGER.info(
                        "ASPECT-end: "
                                + methodName
                                + "() in "
                                + timer.stop().toString()
                                + " with return = "
                                + String.valueOf(retVal)); // $NON-NLS-1$
            }
        }
    }
}
