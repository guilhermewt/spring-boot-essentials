package academy.devdojo.springboot2.requests;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data

public class AnimePostRequestBody {
	
	@NotEmpty(message = "the anime name cannot be empty")
	private String name;
}
