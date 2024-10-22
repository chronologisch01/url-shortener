package com.dkb_code_factory.url_shortener

import com.dkb_code_factory.url_shortener.model.dto.CreateUrlRequest
import com.dkb_code_factory.url_shortener.model.dto.CreateUrlResponse
import com.dkb_code_factory.url_shortener.model.dto.GetUrlResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `test retrieving the original URL`() {
        val url = "https://www.test1.com"
        val request = CreateUrlRequest(url = url)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)

        val createResponse = restTemplate.postForEntity(
            "http://localhost:$port/api/urls",
            entity,
            CreateUrlResponse::class.java
        )

        val shortCode = createResponse.body?.shortCode
        assertNotNull(shortCode)
        assertTrue(shortCode!!.isNotEmpty())

        val getResponse = restTemplate.getForEntity(
            "http://localhost:$port/api/urls/$shortCode",
            GetUrlResponse::class.java
        )

        assertEquals(HttpStatus.OK, getResponse.statusCode)
        assertEquals(url, getResponse.body?.url)
    }

    @Test
    fun `test redirecting to the original URL`() {
        val url = "https://www.example1.com"
        val request = CreateUrlRequest(url = url)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val entity = HttpEntity(request, headers)

        val createResponse = restTemplate.postForEntity(
            "http://localhost:$port/api/urls",
            entity,
            CreateUrlResponse::class.java
        )

        val shortCode = createResponse.body?.shortCode
        assertNotNull(shortCode)
        assertTrue(shortCode!!.isNotEmpty())

        val headersForGet = HttpHeaders().apply { accept = listOf(MediaType.ALL) }
        val getEntity = HttpEntity<Any>(headersForGet)

        val redirectResponse = restTemplate.exchange(
            "http://localhost:$port/api/urls/r/$shortCode",
            HttpMethod.GET,
            getEntity,
            String::class.java
        )

        assertEquals(HttpStatus.FOUND, redirectResponse.statusCode)
        assertEquals(url, redirectResponse.headers.location.toString())
    }
}