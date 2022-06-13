package academy.devdojo.springboot2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;

@Configuration
public class configuration implements CommandLineRunner{

	@Autowired
	private AnimeRepository animeRepository;
	
	@Override
	public void run(String... args) throws Exception {
	
//		Anime a1 = new Anime(1l ,"steins gate");
//		
//		Anime a2 = new Anime(2l,"naruto");
//		Anime a3 = new Anime(3l,"dbz");
//		Anime a4 = new Anime(4l,"over load");
//		Anime a5 = new Anime(5l,"comboy");
//		Anime a6 = new Anime(6l,"drifters");
//		Anime a7 = new Anime(7l,"helsing");
//		Anime a8 = new Anime(8l,"golder kamuy");
//		Anime a9 = new Anime(9l,"god eater");
//		Anime a10 = new Anime(10l,"grand blue");
//		
//		animeRepository.saveAll(Arrays.asList(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10));
	}

}
