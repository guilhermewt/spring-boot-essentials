package academy.devdojo.springboot2.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnimePostRequestBody {
	

	@NotEmpty(message = "the anime name cannot be empty")
	private String name;
}
