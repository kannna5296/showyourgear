package getphotos

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
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

        val s3Client = MyS3Client.create()

        val req = ListObjectsRequest.builder().bucket("localbucket").build()
        val list = s3Client.listObjects(req)

//        val photos = list.contents().forEach {
//            Photo(
//                src = "urlFromEnv" + it.key(),
//                size = Size(
//                    width = it.size(),
//                    height = it.size(), // サイズはどうやって取得するんや？
//                )
//            )
//        }

        val response: APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
            .withHeaders(headers)

        return try {
            val output: String = String.format("{ \"message\": \"Calling getPhotos!\"}")
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
