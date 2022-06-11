package academy.devdojo.springboot2.controller;

import java.util.Collections;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.animeService;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

	@InjectMocks // a classe em si
	private AnimeController animeController;

	@Mock // as classes que estao sendo controlada por animeController
	private animeService animeServiceMock;

	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())).thenReturn(animePage); // quando alguem
																									// executar o
																									// listAll retorna
																									// animePage

		BDDMockito.when(animeServiceMock.listAllNonPageable()).thenReturn(List.of(AnimeCreator.createValidAnime())); // 1

		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
				.thenReturn(AnimeCreator.createValidAnime()); // 2

		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(AnimeCreator.createValidAnime())); // 3
		
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
		.thenReturn(AnimeCreator.createValidAnime()); // 4
		
		BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class)); // 5 put
		
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong()); //6 delete
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

	@Test
	@DisplayName("ListAll return list of anime when successful") // 1
	void listAll_ReturnsListOfAnime_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();

		List<Anime> animes = animeController.listAll().getBody();

		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findById return anime when successful") // 2
	void findById_ReturnsAnime_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		Anime anime = animeController.findById(1).getBody();

		Assertions.assertThat(anime).isNotNull();

		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
	}

	@Test
	@DisplayName("findByName return anime when successful") // 2
	void findByName_ReturnsListOfAnime_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();

		List<Anime> animes = animeController.findByName("anime").getBody();

		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

	}
	
	@Test
	@DisplayName("findByName return an empty list of anime when anime is not found") // 3
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList()); // quando vou fazer test de um comportamento nao desejado eu coloco diretamente no test
		
		List<Anime> animes = animeController.findByName("anime").getBody();

		Assertions.assertThat(animes).isNotNull().isEmpty();

	}
	
	
	@Test
	@DisplayName("save return anime when successful") // 4
	void save_ReturnsAnime_WhenSuccessful() {
		Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

		Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

	}

	@Test
	@DisplayName("replace update anime when successful") // 5
	void replace_UpdatesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
		.doesNotThrowAnyException(); // jeito 1
		
		ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);		

	}
	
	@Test
	@DisplayName("delete removes anime when successful") // 5
	void delete_RemovesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeController.delete(1))
		.doesNotThrowAnyException(); // jeito 1
		
		ResponseEntity<Void> entity = animeController.delete(1); // jeito 2
		
		Assertions.assertThat(entity).isNotNull();
		
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);		

	}

}
