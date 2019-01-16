package easycommand.mbds.unice.fr.eaasycommand.api;

import easycommand.mbds.unice.fr.eaasycommand.api.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserClient {
    @POST("user")
    Call<ResponseBody> createUser(@Body User user);
}

