package vn.finiex.shipperapp.http;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.finiex.shipperapp.model.AccessToken;
import vn.finiex.shipperapp.model.Order;
import vn.finiex.shipperapp.model.Task;
import vn.finiex.shipperapp.model.UserInfo;
import vn.finiex.shipperapp.model.UserLocation;

public class ServerConnector {
	
	private final String BASE_URL = "http://api.finiex.vn/api/";
	private Retrofit retrofit;
	private ShiperServerAPI service;
	
	private AccessToken accessToken;
	
	private static ServerConnector instance;
	
	public synchronized static ServerConnector getInstance() {
		if(instance ==null)
			instance = new ServerConnector();
		return instance;
	}
	
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	public AccessToken getAccessToken() {
		return accessToken;
	}
	
	private ServerConnector() {
		
		retrofit = new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		
		service = retrofit.create(ShiperServerAPI.class);
	}
	
	public void getUserInfo(String userId, String token, Callback<UserInfo> listener){
		Call<UserInfo> call= service.getUserInfo(userId, token);
		call.enqueue(listener);
		
	}
	
	public void getAdminInfo(Callback<UserInfo> listener){
		Call<UserInfo> call= service.getAdminInfo(accessToken.getAccessToken());
		call.enqueue(listener);
		
	}
	
	public void updateUserLocation(String uId, String accessToken, UserLocation data){
		Call<Object> call= service.updateLocation(uId, accessToken, data);
		call.enqueue(new Callback<Object>() {

			@Override
			public void onFailure(Call<Object> arg0, Throwable arg1) {
				arg1.printStackTrace();
				
			}

			@Override
			public void onResponse(Call<Object> arg0, Response<Object> arg1) {
				Log.d("UPDATE_LOCATION", arg1.toString());
				
			}
		});
	}
	
	public List<Task> getAllTask(){
		if(accessToken == null) return null;
		try {
			Response<List<Task>> taskList= service.getAllTask(accessToken.getUserId(), accessToken.getAccessToken()).execute();
			List<Task> lt = taskList.body();
			for (Task task: lt) {
				task.getOrderOnline();
			}
			return lt;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	public List<Order> getOrderOfTask(int id){
		if(accessToken == null) return null;
		try {
			Response<List<Order>> ordersOfTask = service.getOrderTask(id + "", accessToken.getAccessToken()).execute();
			return ordersOfTask.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void uploadImage(String path){
		
		RequestBody file = RequestBody.create(MediaType.parse("image/*"), path);
		
		service.updatePicture(accessToken.getAccessToken(), file).enqueue(new Callback<Object>() {

			@Override
			public void onFailure(Call<Object> arg0, Throwable arg1) {
				arg1.printStackTrace();
			}

			@Override
			public void onResponse(Call<Object> arg0, Response<Object> arg1) {
				System.out.println(arg1.body());
			}
		});;
	}
	
}
