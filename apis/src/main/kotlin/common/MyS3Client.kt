package common

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

class MyS3Client {

    companion object {
        fun create(): S3Client {

            val env = System.getenv()["ENV_NAME"]
            val accessKey = System.getenv()["S3_ACCESS_KEY"]
            val secretKey = System.getenv()["S3_SECRET_KEY"]

            var credentials: AwsCredentials = if (env != null) {
                AwsBasicCredentials.create(accessKey, secretKey)
            } else {
                // Local
                AwsBasicCredentials.create("dummy", "dummy")
            }

            val builder = S3Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))

            // Local
            if (env == null) {
                builder.endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
            }

            return builder.build()
        }
    }
}
