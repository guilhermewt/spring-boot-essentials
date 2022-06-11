package academy.devdojo.springboot2.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
	

	@NotEmpty(message = "the anime name cannot be empty")
	private String name;
}
