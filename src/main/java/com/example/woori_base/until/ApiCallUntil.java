package com.example.woori_base.until;

import com.example.woori_base.exception.BusinessException;
import com.example.woori_base.exception.InternalException;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ApiCallUntil {// dùng để call đến một api của một hệ thống khác

    private final OkHttpClient client = new OkHttpClient();

    // phương thức get
    public String makeGetRequest(String apiUrl) throws IOException {//truyền lên url
        Request request = new Request.Builder()//triển khai buider để bui url
                .url(apiUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {//call và gửi resquest lên là đường dẫn url

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected response: " + response.code());
            }
        }
    }

    //call api gateway với phương thức post
    public String makePostRequest(String apiUrl, String requestBody) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(requestBody, mediaType);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (InternalException | IOException e){
            throw new InternalException("8012",null,"CoreBanking Timeout");
        }
    }
}
