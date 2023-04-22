package helloworld

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AppTest {
    @Test
    fun successfulResponse() {
        val app = App()
        val result: APIGatewayProxyResponseEvent = app.handleRequest(null, null)
        assertEquals(200, result.getStatusCode().intValue())
        assertEquals("application/json", result.getHeaders().get("Content-Type"))
        val content: String = result.getBody()
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}