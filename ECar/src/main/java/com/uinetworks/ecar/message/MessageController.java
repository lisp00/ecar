package com.uinetworks.ecar.message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.annotations.Param;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import lombok.extern.log4j.Log4j;


// 참고 
// https://team-platform.tistory.com/23

@Log4j
@Controller
public class MessageController {
	// 서비스키
	private String serverKey = "AAAAX4tChWo:APA91bHk-NFG1N93O42PrgVQXVQ6LMgBIOS2i_ThParNm7FKadoolZLwNXciPtKu4fX_thEnJoC4_MIxPk6LWfMBS-L02MkMzFjiAxcPrFS5NIvc_hWAs7o-juuvz3Zh5wL0le38U10y";
	private String topic = "weather";
	private String token = "erZxHPnpQGidAxjq-vuPRX:APA91bGyKdBDEnKgnJFetr_RJd4zyZd7TD8dDF7KXz3ARlND2W0PVaDBVqos5VIQZFAN08wFnnzzI2c4Sd_Bc0qMgqYgDcpIZoiBIEk6-VwGbfGfaQzZrI8nHnN4A8OJz_KlO4qND9bF";
	private FirebaseApp firebaseApp = null;
	private String serviceKey = "service-key.json";
	private boolean appInitialized = false;
	
	// 파이어베이스앱 초기화 
	public void firebaseInitializing() {
		InputStream serviceAccount = null;
		FirebaseOptions options = null;
		try {
			ClassPathResource resource = new ClassPathResource(serviceKey);
			serviceAccount = resource.getInputStream();
			options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			log.info("App Name : " + FirebaseApp.initializeApp(options, "fcmmsg").getName());
			appInitialized = true;
		} catch(FileNotFoundException e) {
			log.error("Firebase ServiceAccountKey FileNotFoundException" + e.getMessage());
		} catch(IOException e) {
			log.error("FirebaseOptions IOException" + e.getMessage());
		}
	}
	
	// 푸시메시지 입력화면
	@RequestMapping(value = "/messageSend")
	public void messageSend() {}
	
	// Firebase SDK 사용 푸시 메시지 서버
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/push", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String pushTest(@Param("target") String target, @Param("title")String title, @Param("body") String body) {
		Notification noti = new Notification(target + " " + title, body);
		Message msg = null;
		MulticastMessage multiMsg = null;
		
		if(!appInitialized) {
			firebaseInitializing();
		}
		
		log.info("target : " + target);
		
		if(target.equals("topic")) {
			msg = Message.builder()
					.setTopic(topic)
					.setNotification(noti)
					.build();
		} else if(target.equals("single")) {
			msg = Message.builder()
					.setToken(token)
					.setNotification(noti)
					.build();
		} else if(target.equals("multi")){
			List<String> multiTokens = Arrays.asList(
					"erZxHPnpQGidAxjq-vuPRX:APA91bGyKdBDEnKgnJFetr_RJd4zyZd7TD8dDF7KXz3ARlND2W0PVaDBVqos5VIQZFAN08wFnnzzI2c4Sd_Bc0qMgqYgDcpIZoiBIEk6-VwGbfGfaQzZrI8nHnN4A8OJz_KlO4qND9bF",
					"erZxHPnpQGidAxjq-vuPRX:APA91bGyKdBDEnKgnJFetr_RJd4zyZd7TD8dDF7KXz3ARlND2W0PVaDBVqos5VIQZFAN08wFnnzzI2c4Sd_Bc0qMgqYgDcpIZoiBIEk6-VwGbfGfaQzZrI8nHnN4A8OJz_KlO4qND9bF",
					"erZxHPnpQGidAxjq-vuPRX:APA91bGyKdBDEnKgnJFetr_RJd4zyZd7TD8dDF7KXz3ARlND2W0PVaDBVqos5VIQZFAN08wFnnzzI2c4Sd_Bc0qMgqYgDcpIZoiBIEk6-VwGbfGfaQzZrI8nHnN4A8OJz_KlO4qND9bF"
					);
			multiTokens.forEach(t -> log.info("token : " + t));
			multiMsg = MulticastMessage.builder()
					.setNotification(noti)
					.addAllTokens(multiTokens)
					.build();
		} else {
			log.error("Target selection is wrong");
			return "/messageSend";
		}
		try {
			if(target.equals("topic") || target.equals("single")) {
				String response = FirebaseMessaging.getInstance(FirebaseApp.getInstance("fcmmsg")).send(msg);
				log.info("push success : " + response);
				return "/messageSend";
			} else if(target.equals("multi")){
				BatchResponse response = FirebaseMessaging.getInstance(FirebaseApp.getInstance("fcmmsg")).sendMulticast(multiMsg);
				log.info(response.getSuccessCount() + " messages were sent successfully");
				return "/messageSend";
			}
		} catch (FirebaseMessagingException e) {
			log.error("Firebase FirebaseMessageingException" + e.getMessage());
			e.printStackTrace();
			return "/messageSend";
		} 
		return "/messageSend";
	}
	
	// 기존 Http 방식. 안쓸것임.
	@RequestMapping(value = "/push2", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String pushTest2(@Param("target") String target, @Param("title")String title, @Param("body") String body) throws IOException {
		JsonObject jsonObj = new JsonObject();
		
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(token);
		jsonObj.add("to", jsonElement);
		
		JsonObject notification = new JsonObject();
		notification.addProperty("title", title);
		notification.addProperty("body", body);
		jsonObj.add("notification", notification);
		
		final MediaType mediaType = MediaType.parse("application/json");
		OkHttpClient httpClient = new OkHttpClient();
		Request request = new Request.Builder().url("https://fcm.googleapis.com/fcm/send")
				.addHeader("Content-Type", "application/json; UTF-8")
				.addHeader("Authorization", "key=" + serverKey)
				.post(RequestBody.create(mediaType, jsonObj.toString()))
				.build();
		Response response = httpClient.newCall(request).execute();
		String res = response.body().string();
		log.info("notification response " + res);
		return "/messageSend";
	}
}
