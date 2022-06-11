package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;

public class AnimeCreator {
	
	public static Anime createAnimeToBeSaved() {
		return Anime.builder().name("hajjime no ippo").build();
	}
	
	public static Anime createValidAnime() {
		return Anime.builder().name("hajjime no ippo").id(1l).build();
	}
	
	public static Anime createValidUpdateAnime() {
		return Anime.builder().name("hajjime no ippo 2").id(1l).build();
	}
	
	
}
