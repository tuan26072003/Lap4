package com.hnq40.myapplication.demo4;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InterfaceSelect {
    @GET("get_all_product.php")
    Call<SvrResponseSelect> getPrd();
}
