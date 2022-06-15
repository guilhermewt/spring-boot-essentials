package academy.devdojo.springboot2.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exception.BadRequestException;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {
	@InjectMocks // a classe em si
	private animeService animeService;

	@Mock // as classes que estao sendo controlada por animeController
	private AnimeRepository animeRepositoryMock;

	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
		.thenReturn(animePage);;// quando alguem
						
		BDDMockito.when(animeRepositoryMock.findAll())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));; // 1

		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
				.thenReturn(Optional.of(AnimeCreator.createValidAnime())); // 2

		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
				.thenReturn(List.of(AnimeCreator.createValidAnime())); // 3
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
		.thenReturn(AnimeCreator.createValidAnime()); // 4
				
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class)); //6 delete
	}

	@Test
	@DisplayName("listAll return list of anime inside page object when successful")
	void listAll_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();

		Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));
		Assertions.assertThat(animePage).isNotNull();

		Assertions.assertThat(animePage.toList()).isNotEmpty();

		Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);

		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("listAllNonPageable return list of anime when successful") // 1
	void listAllNonPageable_ReturnsListOfAnime_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();

		List<Anime> animes = animeService.listAllNonPageable();

		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

	}

	@Test
	@DisplayName("findByIdOrThrowBadRequestException return anime when successful") // 2
	void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		Anime anime = animeService.findByIdOrThrowBadRequestException(1);

		Assertions.assertThat(anime).isNotNull();

		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
	}
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException throws badRequestException anime when successful") // 2
	void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
        .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1));
		
	}

	@Test
	@DisplayName("findByName return anime when successful") // 2
	void findByName_ReturnsListOfAnime_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();

		List<Anime> animes = animeService.findByName("anime");

		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
		

	}
	
	@Test
	@DisplayName("findByName return an empty list of anime when anime is not found") // 3
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList()); // quando vou fazer test de um comportamento nao desejado eu coloco diretamente no test
		
		List<Anime> animes = animeService.findByName("anime");

		Assertions.assertThat(animes).isNotNull().isEmpty();
		

	}
	
	
	@Test
	@DisplayName("save return anime when successful") // 4
	void save_ReturnsAnime_WhenSuccessful() {
		Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

		Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

	}

	@Test
	@DisplayName("replace update anime when successful") // 5
	void replace_UpdatesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
		.doesNotThrowAnyException(); // jeito 1	

	}
	
	@Test
	@DisplayName("delete removes anime when successful") // 5
	void delete_RemovesAnime_WhenSuccessful() {
		
		Assertions.assertThatCode(() -> animeService.delete(1))
		.doesNotThrowAnyException(); // jeito 1	

	}
}
