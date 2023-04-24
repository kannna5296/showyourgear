package getphotos

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun successfulResponse() {
        val app = Application()
        val result: APIGatewayProxyResponseEvent = app.handleRequest(null, null)
        assertEquals(200, result.statusCode)
        assertEquals("application/json", result.headers["Content-Type"])
        val content: String = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"Calling getPhotos!\""))
    }
}
