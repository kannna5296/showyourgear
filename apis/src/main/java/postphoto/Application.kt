package postphoto

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent

/**
 * Handler for requests to Lambda function.
 */
class Application : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val headers: MutableMap<String, String> = mutableMapOf()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"

        val response: APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
            .withHeaders(headers)

        return try {
            val output: String = String.format("{ \"message\": \"Calling postPhoto!\"}")
            response
                .withStatusCode(200)
                .withBody(output)
        } catch (e: Exception) {
            response
                .withBody("{ \"message\": \"予期しないエラーが発生しました。ごめんなさい！！！\"}")
                .withStatusCode(500)
        }
    }
}
