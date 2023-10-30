package br.com.erudio.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.exceptions.ExceptionResponse;
import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.exceptions.UnsuportedMathOperationException;

@ControllerAdvice
/*
 * utiliza essa notation para concentrar algum tratamento que seria espalhado em todos
 * os controllers sendo algo mais global.
 */
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> hadleAllExceptions(
			Exception ex, WebRequest request
			){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UnsuportedMathOperationException.class)
	public final ResponseEntity<ExceptionResponse> hadleBadRequestExceptions(
			Exception ex, WebRequest request
			){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ResourceNotFoundEXception.class)
	public final ResponseEntity<ExceptionResponse> hadleNotFoundExceptions(
			Exception ex, WebRequest request
			){
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
}
