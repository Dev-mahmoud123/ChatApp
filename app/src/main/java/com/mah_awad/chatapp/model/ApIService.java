package com.mah_awad.chatapp.model;

import com.mah_awad.chatapp.notification.MyResponse;
import com.mah_awad.chatapp.notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApIService {

    @Headers(
            {
             "Content-Type:application/json",
             "Authorization:key=AAAAqESj09w:APA91bFNnk-kg6Tovr1qsu8QNJ_pwvbFD8rrQ3l95kJ-w8m2Hs7Ta8_4OzktchKl3ntQb8hBTZ4jU6OEWJc4Gyz59ja48Vc5U0CmXnloZujlcIG8OlDgX7XhMAX9I8FF2HM5p5hQ3I4k"
    }
    )

    @POST("fcm/send")
    Call<MyResponse>sendNotification(@Body Sender body);
}
