package com.maersk.errorhandler.rest;

import com.maersk.errorhandler.PaymentsGenericClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.maersk.errorhandler.PaymentsGenericServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR){
            log.error("Server error on provider site path: " + url.getPath());
            throw new PaymentsGenericServerException("Server error on provider site path: " + url.getPath());
        }else if(response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR){
            log.error("Client error on provider site path: " + url.getPath());
            throw  new PaymentsGenericClientException("Client error on provider site path: " + url.getPath(),
                    response.getStatusCode());
        }
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(),
                "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        if(response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR){
            throw new PaymentsGenericServerException("Internal server error on provider site");
        }else if(response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR){
            throw new PaymentsGenericClientException("Client error on provider site", response.getStatusCode());
        }
    }

}
