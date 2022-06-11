package academy.devdojo.springboot2.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.animeService;
import academy.devdojo.springboot2.util.AnimeCreator;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
	
	@InjectMocks // a classe em si
	private AnimeController animeController;
	
	@Mock // as classes que estao sendo controlada por animeController
	private animeService animeServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
		                 .thenReturn(animePage); //quando alguem executar o listAll retorna animePage
	}
	
	@Test
	@DisplayName("List return list of anime inside page object when successful")
	void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		Page<Anime> animePage = animeController.list(null).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		
		Assertions.assertThat(animePage.toList()).isNotEmpty();
		
		Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}

}
