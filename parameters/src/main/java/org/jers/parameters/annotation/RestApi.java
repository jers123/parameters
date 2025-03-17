package org.jers.parameters.annotation;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_PATH;
import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_ANGULAR_PATH;
import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_PATH2;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_ANGULAR__PATH;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_PATH;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_PATH2;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@RestController
@CrossOrigin(origins = {LOCAL_ORIGIN_PATH,
        PUBLIC_ORIGIN_PATH,
        LOCAL_ORIGIN_PATH2,
        PUBLIC_ORIGIN_PATH2,
        LOCAL_ORIGIN_ANGULAR_PATH,
        PUBLIC_ORIGIN_ANGULAR__PATH
        //,"*"
    }
    ,methods = {RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE
    }
)
public @interface RestApi {
}