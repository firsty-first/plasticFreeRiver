package com.example.plasticfreeriver;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/")
    Call<PredictionResponse> getWelcomeMessage();
    @POST("/prediction")
    Call<PredictionResponse> getPrediction(@Body ModelInput input);
}
