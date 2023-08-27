package com.fotografocomvc;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Photographer;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class FotografocomvcApplicationTests {

	@Autowired
	private PhotographerRepository photographerRepository;

	@Autowired
	private BaseUserRepository baseUserRepository;

	@Test
	void contextLoads() {
		BaseUser baseUser = BaseUser.builder()
				.id(1L)
				.email("email")
				.password("password")
				.build();

		baseUserRepository.save(baseUser);

		Photographer photographer = Photographer.builder()
				.name("julio")
				.id(1L)
				.baseUser(baseUser)
				.build();

		photographer = photographerRepository.save(photographer);
		System.out.println(photographer);
//
//		Optional<Photographer> photographer2 =		photographerRepository.findById(1L);
//
//
//		Optional<Photographer> photographer3 = photographerRepository.findById(3L);
//
//		System.out.println(photographer2);
//		System.out.println(photographer3);


	}

}
