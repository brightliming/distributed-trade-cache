/**  
 * All rights Reserved, Designed By www.troila.com
 * @Title:  ValidAspect.java   
 * @Package com.toila.aop   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 卓朗科技_limingliang     
 * @date:   2018年8月10日 上午11:28:33   
 * @version V1.0 
 * @Copyright: 2018 www.troila.com Inc. All rights reserved. 
 * 注意：本内容仅限于天津卓朗科技信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.github.limingliang.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: ValidAspect
 * @Description:TODO(全局异常处理类)
 * @author: 卓朗科技 _limingliang
 * @date: 2018年8月10日 上午11:28:33
 * 
 * @Copyright: 2018 www.troila.com Inc. All rights reserved.
 *             注意：本内容仅限于天津卓朗科技信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Aspect
@Component
public class ValidAspect {
	
	private final Logger logger = LoggerFactory.getLogger(ValidAspect.class);
	
	private ObjectError error;

	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final ExecutableValidator validator = factory.getValidator().forExecutables();

	private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
		return validator.validateParameters(obj, method, params);
	}

	@Pointcut("execution(public * com.github.limingliang.controller.*.*(..))")
	public void valid() {
	}

	// 环绕通知,环绕增强，相当于MethodInterceptor
	@Around("valid()")
	public Object arround(ProceedingJoinPoint pjp) throws Throwable{
		//logger.info("参数校验start.....");
		// 取参数，如果没参数，那肯定不校验了
		Object[] objects = pjp.getArgs();
		if (objects.length == 0) {
			return pjp.proceed();
		}
		/************************** 校验封装好的javabean **********************/
		// 寻找带BindingResult参数的方法，然后判断是否有error，如果有则是校验不通过
		for (Object object : objects) {
			if (object instanceof BeanPropertyBindingResult) {
				// 有校验
				BeanPropertyBindingResult result = (BeanPropertyBindingResult) object;
				if (result.hasErrors()) {
					List<FieldError> list = result.getFieldErrors();
					List<String> errorMsg = new ArrayList<>();
					for (FieldError error : list) {
						logger.error(
								error.getCode() + "---" + error.getField() + "--" + error.getDefaultMessage());
						errorMsg.add(error.getCode() + "---" + error.getField() + "--" + error.getDefaultMessage());
					}
					return JSON.toJSONString(errorMsg);
				}
			}
		}

		/************************** 校验普通参数 *************************/
		// 获得切入目标对象
		Object target = pjp.getThis();
		// 获得切入的方法
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		// 执行校验，获得校验结果
		Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, objects);
		// 如果有校验不通过的
		if (!validResult.isEmpty()) {
			String[] parameterNames = parameterNameDiscoverer.getParameterNames(method); // 获得方法的参数名称
			List<String> errorMsg = new ArrayList<>();
			for (ConstraintViolation<Object> constraintViolation : validResult) {
				PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath(); // 获得校验的参数路径信息
				int paramIndex = pathImpl.getLeafNode().getParameterIndex(); // 获得校验的参数位置
				String paramName = parameterNames[paramIndex]; // 获得校验的参数名称
				logger.error(paramName+"--"+constraintViolation.getMessage());
				errorMsg.add(paramName+"--"+constraintViolation.getMessage());
			}
			return JSON.toJSONString(errorMsg);
		}
		return pjp.proceed();
	}
}
