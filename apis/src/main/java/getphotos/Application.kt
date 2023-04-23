package getphotos

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest

/**
 * Handler for requests to Lambda function.
 */
class Application : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context?): APIGatewayProxyResponseEvent {
        val headers: MutableMap<String, String> = mutableMapOf()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"

        val credentials: AwsCredentials = AwsBasicCredentials.create("accessKey", "secretKey")
        val s3Client = S3Client.builder()
            .region(Region.AP_NORTHEAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()

        val req = GetObjectRequest.builder().bucket("hoge").key("hogefike").build()
        val ob = s3Client.getObject(req)

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
