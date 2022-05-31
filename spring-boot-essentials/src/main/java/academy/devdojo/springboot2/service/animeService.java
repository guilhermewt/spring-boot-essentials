package academy.devdojo.springboot2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class animeService {
	
	//private final AnimeRepository animeRepository;
	
	private List<Anime> anime  = List.of(new Anime(1l,"DBZ"), new Anime(2l,"BERSEKd"));
	
		
	public List<Anime> listAll(){
		return anime;
	}
	
	public Anime findById(long id){
		return anime.stream()
				.filter(anime -> anime.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "anime not found"));
	}
	
	
}
