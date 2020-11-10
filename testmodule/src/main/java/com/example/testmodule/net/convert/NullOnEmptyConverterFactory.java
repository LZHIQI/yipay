package com.example.testmodule.net.convert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @name lzq
 * @class name：com.mican.network.convert
 * @class describe
 * @time 2020/6/3 9:43 AM
 * @change
 * @chang
 * @class describe
 */
public  class NullOnEmptyConverterFactory extends Converter.Factory{

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return (Converter<ResponseBody, Object>) body -> {
            if (body.contentLength() == 0) return null;
            return delegate.convert(body);
        };
    }
}
