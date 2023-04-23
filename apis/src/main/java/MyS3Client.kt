import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

class MyS3Client {

    companion object {
        fun create(): S3Client {
            val credentials: AwsCredentials = AwsBasicCredentials.create("dummy", "dummy")
            return S3Client.builder()
                .region(Region.AP_NORTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                // Localでのみ
                .forcePathStyle(true)
                .endpointOverride(URI.create("http://localhost:4566"))
                .build()
        }
    }
}
