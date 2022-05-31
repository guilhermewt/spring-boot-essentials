package academy.devdojo.springboot2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;

@Service
public class animeService {
	
	//private final AnimeRepository animeRepository;
	
	private static List<Anime> animes;
	
	static {
		animes = new ArrayList<>(List.of(new Anime(1l,"DBZ"), new Anime(2l,"BERSEKd")));
	}

	
		
	public List<Anime> listAll(){
		return animes;
	}
	
	public Anime findById(long id){
		return animes.stream()
				.filter(anime -> anime.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "anime not found"));
	}
	
	public Anime save(Anime anime) {
		anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
		animes.add(anime);
		return anime;
	}
	
	public void delete(long id){
		animes.remove(findById(id));
	}
	
	public void replace(Anime anime){
		animes.remove(findById(anime.getId()));
		animes.add(anime);
	}
	
	
}
