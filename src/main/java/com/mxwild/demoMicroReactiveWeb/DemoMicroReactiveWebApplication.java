package com.mxwild.demoMicroReactiveWeb;

import lombok.Data;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.*;
import reactor.ipc.netty.http.server.HttpServer;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Data
class Hello {
	private final String name;

	Hello(String name) {
		this.name = name;
	}
}

@SpringBootApplication
public class DemoMicroReactiveWebApplication {

	static RouterFunction getRouter() {
		HandlerFunction hello = request -> ok().body(fromObject("Hello"));

		return
				route(
						GET("/"), hello)
						.andRoute(
								GET("/json"), req -> ok()
															.contentType(APPLICATION_JSON)
															.body(fromObject(new Hello("world"))));
	}

	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(DemoMicroReactiveWebApplication.class, args);

		// Создаем обработчик
		//HandlerFunction hello = request -> ok().body(fromObject("Hello"));

		// Создаем роутер
		RouterFunction router = getRouter();

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

		// Создаем сервер
		HttpServer
				.create("localhost", 8080)
				.newHandler(new ReactorHttpHandlerAdapter(httpHandler))
				.block();

		Thread.currentThread().join();

	}
}
