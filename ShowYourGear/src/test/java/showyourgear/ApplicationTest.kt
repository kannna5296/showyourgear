package showyourgear

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

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
        assertTrue(content.contains("\"hello world\""))
    }
}
