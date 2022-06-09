package academy.devdojo.springboot2.repository;

import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import academy.devdojo.springboot2.domain.Anime;

@DataJpaTest
@DisplayName("test for anime repository")
class AnimeRepositoryTest {
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@Test
	@DisplayName("save creates anime when successufull")
	void save_persistAnime_whenSuccessfull() {
		
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
	    
		Assertions.assertThat(animeSaved).isNotNull();	
	    Assertions.assertThat(animeSaved.getId()).isNotNull();
	    Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());	

	}
	
	
	
	private Anime createAnime() {
		return Anime.builder().name("hajjime no ippo").build();
	}

}
