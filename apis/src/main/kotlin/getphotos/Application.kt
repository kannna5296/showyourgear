package getphotos

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.ObjectMapper
import common.MyS3Client
import software.amazon.awssdk.services.s3.model.ListObjectsRequest

/**
 * Handler for requests to Lambda function.
 */
class Application : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val headers: MutableMap<String, String> = mutableMapOf()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"
        val apiAllowOrigin: String = System.getenv()["API_ALLOW_ORIGIN"] ?: throw Exception("Lambdaの環境変数設定が不足しています")
        headers["Access-Control-Allow-Origin"] = apiAllowOrigin

        val s3Client = MyS3Client.create()
        val objectMapper = ObjectMapper()

        val bucketName: String = System.getenv()["BUCKET_NAME"] ?: "localbucket"

        val req = ListObjectsRequest.builder().bucket(bucketName).build()
        val list = s3Client.listObjects(req)

        list.contents().forEach {
            println(it.toString())
        }

        val photos = list.contents().shuffled().take(100).map {
            Photo(
                src = "https://" + bucketName + ".s3.ap-northeast-1.amazonaws.com/" + it.key(),
            )
        }

        val response: APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
            .withHeaders(headers)

        return try {
            response
                .withStatusCode(200)
                .withBody(objectMapper.writeValueAsString(photos))
        } catch (e: Exception) {
            response
                .withBody("{ \"message\": \"予期しないエラーが発生しました。ごめんなさい！！！\"}")
                .withStatusCode(500)
        }
    }
}
