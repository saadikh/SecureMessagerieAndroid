package android.mbds.fr.appct.api.service;

import android.mbds.fr.appct.api.model.Login;
import android.mbds.fr.appct.api.model.Message;
import android.mbds.fr.appct.api.model.ReceivedMsg;
import android.mbds.fr.appct.api.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {
    @POST("login")
    Call<User> login(@Body Login login);

    @GET("fetchMessages")
    Call<List<ReceivedMsg>> getMessages(@Header("Authorization") String authToken);

    @POST("sendMsg")
    Call<ResponseBody> sendMessage(@Header("Authorization") String authToken, @Body Message message);

    @POST("createUser")
    Call<ResponseBody> createUser(@Body Login login);

    @POST("validate")
    Call<ResponseBody> validate(@Header("Authorization") String authToken);
}
