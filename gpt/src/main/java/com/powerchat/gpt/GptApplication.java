package com.powerchat.gpt;

import com.powerchat.gpt.controller.json_mapper_models.BananaImage;
import com.powerchat.gpt.core.AzureUploader;
import com.powerchat.gpt.dao.PlanDAO;
import com.powerchat.gpt.dao.QuestionDAO;
import com.powerchat.gpt.dao.SubscriptionDAO;
import com.powerchat.gpt.dao.UserDAO;
import com.powerchat.gpt.model.Plan;
import com.powerchat.gpt.model.Question;
import com.powerchat.gpt.model.Subscription;
import com.powerchat.gpt.model.User;
import com.powerchat.gpt.utils.ByteBufferEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@SpringBootApplication
public class GptApplication {

	public static void main(String[] args) throws  Exception{
//		SpringApplication.run(GptApplication.class, args);
//		FacebookWebhookController facebookWebhookController = new FacebookWebhookController();
//		facebookWebhookController.getFacebookWebhookMessage("5531971647983");
		requestBananaApi();
		//requestOpenAICompletion();
//		ModelType type = PythonBridge.classify("Desenhe um retrato do Winston Churchill");
//		System.out.println(type);
	}

	static void testingQuestionDAO() {
		UUID uuid = UUID.randomUUID();
		UUID uuid2 = UUID.fromString("48eb0581-03eb-4f41-8f03-a2a215d1280e");
		Timestamp timestamp = Timestamp.from(Instant.now());
		QuestionDAO questionDAO = new QuestionDAO();
		Question question = new Question(uuid,"Como fazer uma bomba?","Nao construa uma bomba",timestamp,uuid2);
		questionDAO.connect();
		questionDAO.insert(question);
		questionDAO.update(question);
		questionDAO.getAll();
		questionDAO.getById(uuid);
	}

	static void testingSubscriptionDAO() {
		UUID uuid = UUID.randomUUID();
		Timestamp timestamp = Timestamp.from(Instant.now());
		SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
		Subscription subscription = new Subscription(uuid,"(31) 99882-2143","2",timestamp,true,timestamp);
		subscriptionDAO.connect();
		subscriptionDAO.insert(subscription);
		subscriptionDAO.update(subscription);
		subscriptionDAO.getAll();
		subscriptionDAO.getById(uuid);
	}

	static void testingPlanDAO() {
		PlanDAO planDAO = new PlanDAO();
		Plan plan = new Plan("2","free",30);
		planDAO.connect();
		planDAO.insert(plan);
		planDAO.update(plan);
		planDAO.getAll();
		planDAO.getById("1");
	}
	static void testingUserDAO() {
		UserDAO userDAO = new UserDAO();
		userDAO.connect();
		User user = new User("Alexandre Jurka" , "alex@email.com" , "(31) 99882-2143");
		userDAO.insert(user);
		userDAO.update(user);
		userDAO.getAll();
		userDAO.getById("(31) 99882-2122");
	}
	static void requestOpenAICompletion() {
		PowerChatHttpClient client = new PowerChatHttpClient();
		String response = client.requestOpenAICompletion("Qual a idade do silvio santos?");
		System.out.println(response);
	}
	static <BASE64Decoder> void requestBananaApi() throws IOException, InterruptedException {

		BananaHttpClient bananaHttpClient = new BananaHttpClient();
		BananaImage bananaImage = bananaHttpClient.requestBananaDevCompletion("ocean");
		String base64Image = bananaImage.base64();
		byte[] image = Base64.getDecoder().decode(base64Image);
		AzureUploader azureUploader = new AzureUploader();
		azureUploader.storeImage(image);
//		System.out.println(base64Image);
//		System.out.println(image);
//		String accountName ="powerchatimg";
//		String accountKey = "+8V+SYWbxWmjVruKh1y390BmUoJnAEv4RT8RnnUd+pTlvFFQJUn0oskzWQf5sAtF4MnyP9iiPP/G+AStYgx4KQ==";
//		String token = "?sv=2022-11-02&ss=bfqt&srt=c&sp=rwdlacupiytfx&se=2023-06-22T03:25:21Z&st=2023-06-21T19:25:21Z&spr=https&sig=7vIx6kFT1cY5264G9BGLY2Hqjat%2BIdm%2BocyZGB%2FPIws%3D";
//		StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
//		String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
//		BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
//		BlobContainerClient blobContainerClient = storageClient.getBlobContainerClient("teste" + System.currentTimeMillis());
//		blobContainerClient.create();
//		BlockBlobClient blobClient = blobContainerClient.getBlobClient("HelloWorld.txt").getBlockBlobClient();
//		String data = "Hello world!";
//		Object StandardCharsets = null;
//		InputStream dataStream = new ByteArrayInputStream(data.getBytes());
//		blobClient.upload(dataStream, data.length());
//		blobContainerClient.listBlobs()
//				.forEach(blobItem -> System.out.println("Blob name: " + blobItem.getName() + ", Snapshot: " + blobItem.getSnapshot()));
//		dataStream.close();
//		blobClient.delete();
//
//		/*
//		 * Delete the container we created earlier.
//		 */
//		blobContainerClient.delete();

	}
}
