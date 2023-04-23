package common

import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import kotlin.test.Test
import kotlin.test.assertTrue

class S3ClientTest {

    private val bucketName = "localbucket"

    @Test
    fun `S3からオブジェクトのリストが取得できる`() {
        val s3Client = MyS3Client.create()
        s3Client.use {
            val req = ListObjectsRequest.builder().bucket(bucketName).build()
            val list = s3Client.listObjects(req)
            assertTrue { list.contents().size > 0 }
            list.contents().forEach {
                println(it.key())
            }
        }
    }
}
