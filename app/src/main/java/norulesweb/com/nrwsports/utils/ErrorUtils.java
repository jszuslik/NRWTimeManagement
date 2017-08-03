package norulesweb.com.nrwsports.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import norulesweb.com.nrwsports.api.ApiWrapper;
import norulesweb.com.nrwsports.model.APIError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {


    public static APIError parseError(Response<?> response) {
        ApiWrapper api = new ApiWrapper();

        Converter<ResponseBody, APIError> converter =
                ApiWrapper.retrofit()
                    .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;

    }

}
