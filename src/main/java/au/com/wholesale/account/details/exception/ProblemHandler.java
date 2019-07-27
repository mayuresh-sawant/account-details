package au.com.wholesale.account.details.exception;

import org.zalando.problem.spring.web.advice.general.GeneralAdviceTrait;
import org.zalando.problem.spring.web.advice.http.HttpAdviceTrait;
import org.zalando.problem.spring.web.advice.io.IOAdviceTrait;
import org.zalando.problem.spring.web.advice.routing.RoutingAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ValidationAdviceTrait;

public interface ProblemHandler extends GeneralAdviceTrait,
        HttpAdviceTrait,
        IOAdviceTrait,
        RoutingAdviceTrait,
        ValidationAdviceTrait {
}
