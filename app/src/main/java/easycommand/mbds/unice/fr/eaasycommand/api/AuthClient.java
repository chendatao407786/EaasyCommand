package easycommand.mbds.unice.fr.eaasycommand.api;

import easycommand.mbds.unice.fr.eaasycommand.api.model.Auth;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthClient {
    @POST("auth")
    Call<ResponseBody> signIn(@Body Auth auth);
}
