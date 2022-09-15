package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        try {
            Map<String, String> pathParameters = input.getQueryStringParameters();
            String time  = pathParameters.get("time");
            String[] arr = time.split(":");
            int hour = Integer.parseInt(arr[0]);
            int min = Integer.parseInt(arr[1]);
    String sendResponse = printWords(hour, min);
            return response
                    .withStatusCode(200)
                    .withBody(sendResponse);
        } catch (Exception e) {
            return response
                    .withBody("{}")
                    .withStatusCode(500);
        }
    }
    private String printWords(int h, int m) {
        List<String> nums = Arrays.asList("zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine",
                "ten", "eleven", "twelve", "thirteen",
                "fourteen", "fifteen", "sixteen", "seventeen",
                "eighteen", "nineteen", "twenty", "twenty one",
                "twenty two", "twenty three", "twenty four",
                "twenty five", "twenty six", "twenty seven",
                "twenty eight", "twenty nine");

        String hour = nums.get(h % 12);
        String result = null;

        if (m == 0){
            System.out.println(hour + " " + " o'clock ");
            result = hour + " o'clock ";
        }
//        //done
        else if (m == 05){
            System.out.println("Five past" + hour);
            result = "Five past" + hour;
        }
        //done
        else if (m == 10){
            result = "Ten past" + hour;
        }
        // done
        else if (m == 25){
            result = "twenty five past" + nums.get((h % 12));
        }
        else if (m == 30) {
            result = "half past"   + hour;
        }
        //done
        else if (m == 55){
            result = "Five to " + nums.get((h % 12) + 1);
        }
        return result;
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
