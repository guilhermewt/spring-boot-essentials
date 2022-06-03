package academy.devdojo.springboot2.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class animeService {
	
	//private final AnimeRepository animeRepository;
	
	private final AnimeRepository animesRepository;
	
	public List<Anime> listAll(){
		return animesRepository.findAll();
	}
	
	public Anime findByIdOrThrowBadRequestException(long id){
		return animesRepository.findById(id)				
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "anime not found"));
	}			
	
	public Anime save(AnimePostRequestBody animePostRequestBody) {
		
		return animesRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
	}
	
	public void delete(long id){
		animesRepository.delete(findByIdOrThrowBadRequestException(id));
	}
	
	public void replace(AnimePutRequestBody animePutRequestBody){
        Anime saveAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
       Anime anime = animesRepository.save(AnimeMapper.INSTANCE.toAnime(animePutRequestBody));
       anime.setId(saveAnime.getId());
		animesRepository.save(anime);
	}
	
	
}
