package easycommand.mbds.unice.fr.eaasycommand.api.model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MenuApi {
    @GET("restaurant/menu/{id_resto}")
    Call<ResponseBody> getMenu(@Path("id_resto") String id_Resto);
}