package academy.devdojo.springboot2.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadRequestExceptionDatails extends ExceptionDetails{
	
	private String title;
	private int status;
	private String details;
	private String developerMessage;
	private LocalDateTime timestamp;
}
