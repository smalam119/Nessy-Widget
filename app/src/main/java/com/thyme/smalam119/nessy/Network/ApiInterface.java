package com.thyme.smalam119.nessy.Network;

import com.thyme.smalam119.nessy.Model.QuotesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by smalam119 on 1/11/18.
 */

public interface ApiInterface {
    @GET("qod")
    Call<QuotesResponse> getQuote(@Query("category") String category);
}
