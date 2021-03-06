package sns.exception;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import sns.exception.HttpFailureResponse.FailureType;

@Component
public class ExceptionProcessor implements Processor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionProcessor.class);
	private static final int CODE_TECHNICAL_EX = 2000;
	private static final int CODE_BUSINESS_EX = 1000;
	@Override
	public void process(Exchange arg0) throws Exception {
		HttpFailureResponse response = null;
		Exception exception = (Exception) arg0.getProperties().get("CamelExceptionCaught");

			if (exception instanceof BusinessException) {
				BusinessException be = (BusinessException) exception;
				response = new HttpFailureResponse(CODE_BUSINESS_EX, be.getMessage(), be.getClass().toString(),
						FailureType.Business);
				LOGGER.error(exception.getMessage(), exception);
			} else {
				response = new HttpFailureResponse(CODE_TECHNICAL_EX, exception.getMessage(),
						exception.getClass().toString(), FailureType.Technical);
			}

			arg0.getOut().setBody(response);
	}

}
