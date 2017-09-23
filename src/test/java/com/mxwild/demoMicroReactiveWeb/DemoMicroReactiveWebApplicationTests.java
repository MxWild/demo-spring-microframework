package com.mxwild.demoMicroReactiveWeb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoMicroReactiveWebApplicationTests {

/*
	@Test
	public void contextLoads() {
	}
*/

	private final WebTestClient webTestClient = WebTestClient
			.bindToRouterFunction(DemoMicroReactiveWebApplication
									.getRouter())
									.build();

	@Test
	public void indexPage_WhenRequested_SaysHello() {
		webTestClient.get().uri("/").exchange()
										.expectStatus().is2xxSuccessful()
										.expectBody(String.class)
										.isEqualTo("Hello");

	}

	@Test
	public void jsonPage_WhenRequested_SaysHello() {
		webTestClient.get().uri("/json").exchange()
				.expectStatus().is2xxSuccessful()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody(Hello.class)
				.isEqualTo(new Hello("world"));
	}

}
