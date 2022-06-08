package academy.devdojo.springboot2.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {

	public static void main(String[] args) {
		//pode colocar o id deste jeito ou animes/2 deste jeito o spring arrumar para gente
		ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class,2);
		log.info(entity);

		Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class,2);
		log.info(object);
		
		Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
		log.info(Arrays.toString(animes));
		
		
		
		//para pegar grandes quantidades de dados o getObject so aceita Arrays entao temos que fazer a conversao com o
		ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {});
		
		log.info(exchange.getBody());
	}

}
