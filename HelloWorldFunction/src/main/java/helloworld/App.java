package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context)
    {
        String requestBody = input.getBody();
        Gson gson = new Gson();

        Map<String, String> userDetails = gson.fromJson(requestBody, Map.class);
        userDetails.put("userID", UUID.randomUUID().toString());

        Map returnValue = new HashMap();
        returnValue.put("Name", userDetails.get("Name"));
        returnValue.put("userID", userDetails.get("userID"));

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.withStatusCode(200);
        response.withBody(userDetails.toString());

        Map responseHeader = new HashMap();
        responseHeader.put("Content-Type", "application/json");

        response.withHeaders(responseHeader);

        return response;
    }
}
