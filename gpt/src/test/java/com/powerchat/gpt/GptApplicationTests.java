package com.powerchat.gpt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class GptApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void requestChatGPT() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create("http://foo.com/"))
		         .build();
		HttpClient client = HttpClient.newBuilder()
		        .version(Version.HTTP_1_1)
		        .followRedirects(Redirect.NORMAL)
		        .connectTimeout(Duration.ofSeconds(20))
		        .build();
		   HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		   System.out.println(response.statusCode());
		   System.out.println(response.body());
	}

}
