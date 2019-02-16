package com.example.greeshma.bloodbank;



import com.example.greeshma.bloodbank.model.SigninModel;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by allwyn on 28-01-2016.
 */
public class ApiClient {

    private static AlertPolceInterface AlertPolceInterface;

    public static AlertPolceInterface getAlertPolceInterface() {
        if (AlertPolceInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
                    "http://www.trusteducare.com/bloodbank/").build();

            AlertPolceInterface = restAdapter.create(AlertPolceInterface.class);
        }
        return AlertPolceInterface;
    }


    public interface AlertPolceInterface {


       @GET("saveuser.php")
        void getBloodSignup(@Query("Name") String Name, @Query("Mobile") String Mobile,@Query("BloodGroup")String BloodGroup,@Query("DOB") String DOB,@Query("Gender")String Gender,
                            @Query("Place") String Place,@Query("Area")String Area, Callback<SigninModel> callback);


    }

}
