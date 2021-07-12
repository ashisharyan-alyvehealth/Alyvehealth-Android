package com.health.immunity.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.health.immunity.IConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements IConstant {

    private static RetrofitClient uniqInstance;
    private ApiInterface apiInterface;
    static HttpLoggingInterceptor logger = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    public static OkHttpClient.Builder okkk = new OkHttpClient.Builder().connectTimeout(30000, TimeUnit.MILLISECONDS).readTimeout(30000, TimeUnit.MILLISECONDS);
    static boolean loggerenable=true;
    Retrofit retrofit;


    public static synchronized RetrofitClient getUniqInstance() {
        if (uniqInstance == null) {
            uniqInstance = new RetrofitClient();
        }
        return uniqInstance;
    }

    public static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("X-Requested-With", "XMLHttpRequest");
           // builder.addHeader("Authorization", "" + GlobalData.accessToken);

            return chain.proceed(builder.build());
        }
    }

    private void ApiClient() {
        try {
          /*  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .build();



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();*/
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if(loggerenable)
                okkk.addInterceptor(logger);
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        //.client(getDefaultOkHttpClient())
                        .addConverterFactory(GsonConverterFactory.create(gson)).client(okkk.build())

                        .build();
            }
            apiInterface = retrofit.create(ApiInterface.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ApiInterface getApi() {
        if (apiInterface == null) {
            ApiClient();
        }
        return apiInterface;
    }
}
