package org.castor.employee.annotation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.castor.employee.utils.SystemConstants.LOCAL_ORIGIN_PATH;
import static org.castor.employee.utils.SystemConstants.PUBLIC_ORIGIN_PATH;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@CrossOrigin(origins = {LOCAL_ORIGIN_PATH,
		PUBLIC_ORIGIN_PATH
		,"*"	}
	,methods = {RequestMethod.GET,
		RequestMethod.POST,
		RequestMethod.PUT,
		RequestMethod.DELETE
	}
)
public @interface RestApi {
}