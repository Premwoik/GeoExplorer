package example.prw.gamegeocos.api.service;

import example.prw.gamegeocos.api.model.SecurityToken;
import example.prw.gamegeocos.api.model.UserCredentials;
import example.prw.gamegeocos.api.model.UserRegistrationForm;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by prw on 16.04.18.
 */

public interface UserService {

    @POST("/user-management/create-user")
    Observable<Response<Void>> register(@Body UserRegistrationForm registrationForm);

    @POST("/login")
    Observable<SecurityToken> login(@Body UserCredentials credentials);

    @GET("/user-management/list-users")
    Observable<ResponseBody> getUsers(@Header("Authorization") String token);
}
