package com.auchris.shoppingcart.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpHeaders.USER_AGENT;

/*
These tests require that the application is running at http://localhost:8080
 */


public class ShoppingCartControllerTest {

    @Test
    public void givenUserIsNotAuthenticated_whenUserInfoIsRetrieved_thenNullResponseReceived()
            throws ClientProtocolException, IOException {

        //Given
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/user/");

        //When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        HttpEntity entity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");

        //Then
        assertEquals("{\"id\":null,\"name\":null,\"role\":null}", responseString);
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void givenGoodCredentials_whenUserAuthenticates_then200IsReceived()
            throws ClientProtocolException, IOException {

        //Given
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("username", "chris"));
        postParameters.add(new BasicNameValuePair("password", "chris"));

        HttpPost request = new HttpPost( "http://localhost:8080/login");
        request.setHeader("User-Agent", USER_AGENT);
        request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

        //When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        //Then
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void givenBadCredentials_whenUserFailsAuthentication_then401IsReceived()
            throws ClientProtocolException, IOException {

        //Given
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("username", "badname"));
        postParameters.add(new BasicNameValuePair("password", "badpassword"));

        HttpPost request = new HttpPost( "http://localhost:8080/login");
        request.setHeader("User-Agent", USER_AGENT);
        request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

        //When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        //Then
        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_UNAUTHORIZED);
    }
}