package helloworld

import java.io.BufferedReader

/**
 * Handler for requests to Lambda function.
 */
class App : RequestHandler<APIGatewayProxyRequestEvent?, APIGatewayProxyResponseEvent?> {
    fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val headers: Map<String, String> = HashMap()
        headers.put("Content-Type", "application/json")
        headers.put("X-Custom-Header", "application/json")
        val response: APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
            .withHeaders(headers)
        return try {
            val pageContents = getPageContents("https://checkip.amazonaws.com")
            val output: String = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents)
            response
                .withStatusCode(200)
                .withBody(output)
        } catch (e: IOException) {
            response
                .withBody("{}")
                .withStatusCode(500)
        }
    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        val url = URL(address)
        BufferedReader(InputStreamReader(url.openStream())).use { br ->
            return br.lines().collect(Collectors.joining(System.lineSeparator()))
        }
    }
}