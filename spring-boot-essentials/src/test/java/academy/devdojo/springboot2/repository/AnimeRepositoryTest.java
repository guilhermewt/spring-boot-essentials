package academy.devdojo.springboot2.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import academy.devdojo.springboot2.domain.Anime;
import lombok.extern.log4j.Log4j2;
 
@Log4j2
@DataJpaTest
@DisplayName("test for anime repository")
class AnimeRepositoryTest {
	
	@Autowired
	private AnimeRepository animeRepository;
	
	
	@Test
	@DisplayName("save persist anime when successufull")
	void save_persistAnime_whenSuccessfull() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
	    
		Assertions.assertThat(animeSaved).isNotNull();	
	    Assertions.assertThat(animeSaved.getId()).isNotNull();
	    Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());	

	}
	

	@Test
	@DisplayName("save updates anime when successufull")
	void save_updateAnime_whenSuccessfull() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		animeSaved.setName("overlord");
		
		Anime animeUpdate = this.animeRepository.save(animeSaved);
	    
		//log.info(animeUpdate.getName());
		Assertions.assertThat(animeUpdate).isNotNull();	
	    Assertions.assertThat(animeUpdate.getId()).isNotNull();
	    Assertions.assertThat(animeSaved.getName()).isEqualTo(animeUpdate.getName());	

	}
	
	@Test
	@DisplayName("delete removes anime when successufull")
	void delete_RemovesAnime_whenSuccessfull() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		this.animeRepository.delete(animeSaved);
		
		Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
		
		Assertions.assertThat(animeOptional).isEmpty();

	}
	
	@Test
	@DisplayName("find by name Return list of anime when successufull")
	void findByName_ReturnsListofAnime_whenSuccessfull() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		String name = animeSaved.getName();
		
		List<Anime> animes = this.animeRepository.findByName(name);
		
		
		
		Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);


	}
	
	@Test
	@DisplayName("find by name Return empty list when no anime is found")
	void findByName_ReturnsListEmpty_whennoAnimeIsNotFound() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		
		List<Anime> animes = this.animeRepository.findByName("xaxa");
			
		Assertions.assertThat(animes).isEmpty();
		

	}
	
	@Test
	@DisplayName("save throw ContraintsValidationException when name is empty")
	void save_throwsContraintsValidationException_WhenNameisEmpty() {
		
		Anime anime = new Anime();

		/* 1 jeito
		Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
	         .isInstanceOf(ConstraintViolationException.class);
	    */
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(anime))
        .withMessageContaining("The anime name cannot be empty");

	}
	
	
	private Anime createAnime() {
		return Anime.builder().name("hajjime no ippo").build();
	}

}
