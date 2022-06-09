package academy.devdojo.springboot2.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
		//exchange e new ParameterizedTypeReference
		ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {});
		
		log.info(exchange.getBody());
		
		
		
//		Anime kingDom = Anime.builder().name("kingDom").build();
//		Anime KingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingDom, Anime.class);
//		log.info("saved anime {}", KingdomSaved);
		
		
		
		//salvando com exchange
		Anime samuraiChamploo = Anime.builder().name("samurai champloo").build();
		ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
				HttpMethod.POST,
				new HttpEntity<>(samuraiChamploo,createJsonHeader()),
				Anime.class);
		
		log.info("saved anime {}", samuraiChamplooSaved);
		
		Anime animeToBeUpdate  = samuraiChamplooSaved.getBody();
		animeToBeUpdate.setName("samurai champlo 2");
		
		ResponseEntity<Void> samuraiChamplooUpdate = new RestTemplate().exchange("http://localhost:8080/animes/",
				HttpMethod.PUT,
				new HttpEntity<>(animeToBeUpdate,createJsonHeader()),
				Void.class);
		
		
		
		log.info(samuraiChamplooUpdate);
		

		ResponseEntity<Void> samuraiChamplooDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
				HttpMethod.DELETE,
				null,
				Void.class,
				animeToBeUpdate.getId());
		
		log.info(samuraiChamplooDelete);
		
	}
	

	 private static HttpHeaders createJsonHeader() {
	        HttpHeaders httpHeaders = new HttpHeaders();
	        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	        return httpHeaders;
	    }
}
