package key.software.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomExceptionDetails> resourceNotFoundExceptionHandler(ResourceNotFoundException userException, WebRequest wr)
	{
		CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(LocalDateTime.now(), userException.getMessage(), wr.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(customExceptionDetails, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<CustomExceptionDetails> badRequestExceptionHandler(BadRequestException userException, WebRequest wr)
	{
		CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(LocalDateTime.now(), userException.getMessage(), wr.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(customExceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<CustomExceptionDetails> alreadyExistsExceptionHandler(AlreadyExistsException userException, WebRequest wr)
	{
		CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(LocalDateTime.now(), userException.getMessage(), wr.getDescription(false));
		return new ResponseEntity<CustomExceptionDetails>(customExceptionDetails, HttpStatus.CONFLICT);
	}

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomExceptionDetails> allExceptionHandler(Exception exception,WebRequest wr)
	{
		CustomExceptionDetails customExceptionDetails = new CustomExceptionDetails(LocalDateTime.now(), exception.getMessage(), wr.getDescription(false));
		return  new ResponseEntity<CustomExceptionDetails>(customExceptionDetails, HttpStatus.PRECONDITION_FAILED);
	}
	
	
	
	

}