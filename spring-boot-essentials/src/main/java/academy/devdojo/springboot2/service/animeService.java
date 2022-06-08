package academy.devdojo.springboot2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
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
	
	
	public Page<Anime> listAll(Pageable pageable){
		return animesRepository.findAll(pageable);
	}
	
	public List<Anime> listAllNonPageable(){
		return animesRepository.findAll();
	}
	
	public List<Anime> findByName(String name){
		return animesRepository.findByName(name);
	}
	
	public Anime findByIdOrThrowBadRequestException(long id){
		return animesRepository.findById(id)				
				.orElseThrow(() -> new BadRequestException("anime not found"));
	}			
	
	@Transactional
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
