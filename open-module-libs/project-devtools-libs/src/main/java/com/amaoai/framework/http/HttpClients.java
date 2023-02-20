package com.amaoai.framework.http;

/* ************************************************************************
 *
 * Copyright (C) 2020 Vincent Luo All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not useEnv this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/* Creates on 2022/8/8. */
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.amaoai.framework.Asserts;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Vincent Luo
 */
public class HttpClients {

    private static final int READ_TIMEOUT = 10; // seconds
    private static final int CONNECTION_TIMEOUT = 10; // seconds

    enum ResponseBodyEnumerateType {
        BYTE,
        STRING,
        BYTE_STRING,
        BYTE_STREAM,
        CHAR_STREAM,
    }

    /**
     * 指定URL发起一次简单GET请求。
     *
     * @param url    请求URL
     * @param _class 序列化指定Class对象
     * @return 序列化后的对象
     */
    public static <T> T get(String url, Class<T> _class) {
        return get(url, (Map<String, String>) null, _class);
    }

    /**
     * 指定URL发起一次复杂GET请求，请求成功后将返回结果序列化成指定Class对象。
     *
     * @param url     请求URL
     * @param headers 请求头，如果没有可以传空或者null
     * @param _class  序列化指定Class对象
     * @return 序列化后的对象
     */
    public static <T> T get(String url, Map<String, String> headers, Class<T> _class) {
        return JSONObject.parseObject(get(url, headers), _class);
    }

    /**
     * 指定URL发起一次GET请求，返回请求结果
     *
     * @param url 请求URL
     * @return 请求结果，返回JSON字符串
     */
    public static String get(String url) {
        return get(url, (Map<String, String>) null);
    }

    /**
     * 指定URL参数发起一次GET请求。
     *
     * @param url     请求url
     * @param headers 请求头，如果没有可以传空或者null
     * @return 请求结果，返回JSON字符串
     */
    public static String get(String url, Map<String, String> headers) {
        return (String) get0(url, headers, ResponseBodyEnumerateType.STRING);
    }

    /**
     * 指定URL参数发起一次GET请求，参数不可携带到RequestBody中，
     * 参数只能在url填写。
     *
     * @param url     请求url
     * @param headers 请求头，如果没有可以传空或者null
     * @return 请求结果，返回JSON字符串
     */
    @SuppressWarnings("SameParameterValue")
    private static Object get0(String url, Map<String, String> headers,
                               ResponseBodyEnumerateType responseBodyType) {
        Object retval;

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();

        /* 添加 Header */
        if (headers != null && !headers.isEmpty())
            headers.forEach(requestBuilder::addHeader);

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            /* 根据枚举类型获取响应数据 */
            retval = getResponseObject(response, responseBodyType);
            /* 断言请求是否成功 */
            Asserts.throwIfBool(response.isSuccessful(), "HTTP请求出错, CODE: {}, URL: {}, MESSAGE: {}",
                    response.code(), url, retval);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return retval;
    }

    /**
     * 指定URL发起一次简单POST请求，将返回结果序列化成指定Class对象
     *
     * @param url    请求URL
     * @param _class 序列化指定Class对象
     * @return 序列化后的对象
     */
    public static <T> T post(String url, Class<T> _class) {
        return post(url, (Object) null, null, _class);
    }

    /**
     * 指定URL发起一次简单POST请求，将返回结果序列化成指定Class对象
     *
     * @param url              请求URL
     * @param requestBodyParam 请求参数
     * @param _class           序列化指定Class对象
     * @return 序列化后的对象
     */
    public static <T> T post(String url, Object requestBodyParam, Class<T> _class) {
        return post(url, requestBodyParam, null, _class);
    }

    /**
     * 指定URL发起一次复杂POST请求，所有的请求数据都可作为参数传递：请求体、请求头。
     * 请求成功后将返回结果序列化成指定Class对象。
     *
     * @param url              请求URL
     * @param requestBodyParam 请求参数
     * @param headers          请求头，如果没有可以传空或者null
     * @param _class           序列化指定Class对象
     * @return 序列化后的对象
     */
    public static <T> T post(String url, Object requestBodyParam, Map<String, String> headers, Class<T> _class) {
        return JSONObject.parseObject(post(url, requestBodyParam, headers), _class);
    }

    /**
     * 指定URL发起一次POST请求，返回请求结果
     *
     * @param url 请求URL
     * @return 请求结果，返回JSON字符串
     */
    public static String post(String url) {
        return post(url, (Object) null, (Map<String, String>) null);
    }

    /**
     * 指定URL发起一次POST请求，返回请求结果
     *
     * @param url              请求URL
     * @param requestBodyParam 请求参数
     * @return 请求结果，返回JSON字符串
     */
    public static String post(String url, Object requestBodyParam) {
        return post(url, requestBodyParam, (Map<String, String>) null);
    }

    /**
     * 指定URL参数发起一次post请求，请求可携带body参数以及请求头。
     *
     * @param url              请求url
     * @param requestBodyParam 请求的body参数，该参数可以是String字符串，也可以是一个对象
     * @param headers          请求头，如果没有可以传空或者null
     * @return 请求结果，返回JSON字符串
     */
    public static String post(String url, Object requestBodyParam, Map<String, String> headers) {
        return (String) post0(url, requestBodyParam, headers, ResponseBodyEnumerateType.STRING);
    }

    /**
     * 指定URL参数发起一次post请求，请求可携带body参数以及请求头。
     *
     * @param url              请求url
     * @param requestBodyParam 请求的body参数，该参数可以是String字符串，也可以是一个对象
     * @param headers          请求头，如果没有可以传空或者null
     * @return 请求结果，返回JSON字符串
     */
    @SuppressWarnings("SameParameterValue")
    private static Object post0(String url, Object requestBodyParam, Map<String, String> headers,
                                ResponseBodyEnumerateType responseBodyType) {
        Object retval;

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                requestBodyParam instanceof String ? (String) requestBodyParam : JSON.toJSONString(requestBodyParam));

        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(requestBody);

        /* 添加 Header */
        if (headers != null && !headers.isEmpty())
            headers.forEach(requestBuilder::addHeader);

        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            /* 根据枚举类型获取响应数据 */
            retval = getResponseObject(response, responseBodyType);
            /* 断言请求是否成功 */
            Asserts.throwIfBool(response.isSuccessful(), "HTTP请求出错, CODE: {}, URL: {}, REQUEST BODY: {}, MESSAGE: {}",
                    response.code(), url, JSON.toJSONString(requestBodyParam), retval);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return retval;
    }

    /**
     * 获取响应数据，通过枚举类型判断请求需要获取什么样的数据从而返回出去。
     *
     * @param response         响应对象
     * @param responseBodyType 响应数据类型
     */
    private static Object getResponseObject(Response response, ResponseBodyEnumerateType responseBodyType)
            throws IOException {
        ResponseBody body = response.body();

        if (body != null) {
            switch (responseBodyType) {
                case BYTE:        return body.bytes();
                case STRING:      return body.string();
                case BYTE_STRING: return body.byteString();
                case BYTE_STREAM: return body.byteStream();
                case CHAR_STREAM: return body.charStream();
            }
        }

        return null;
    }

}
