package com.dkb_code_factory.url_shortener.controller

import com.dkb_code_factory.url_shortener.model.dto.CreateUrlRequest
import com.dkb_code_factory.url_shortener.model.dto.CreateUrlResponse
import com.dkb_code_factory.url_shortener.service.UrlShortenerService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UrlShortenerController::class)
class UrlShortenerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var urlShortenerService: UrlShortenerService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should return shortened url when shortenUrl is called`() {
        // given
        val originalUrl = "https://example.com"
        val shortCode = "abc123"
        val request = CreateUrlRequest(originalUrl)
        val response = CreateUrlResponse(shortCode)

        given(urlShortenerService.createShortUrl(originalUrl)).willReturn(shortCode)

        // when and then
        mockMvc.perform(
            post("/api/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(content().json(objectMapper.writeValueAsString(response)))
    }

    @Test
    fun `should return original url when getOriginalUrl is called with valid shortId`() {
        // given
        val shortCode = "abc123"
        val originalUrl = "https://example.com"
        val response = CreateUrlRequest(originalUrl)

        given(urlShortenerService.getOriginalUrl(shortCode)).willReturn(originalUrl)

        // when and then
        mockMvc.perform(get("/api/urls/$shortCode"))
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(response)))
    }

    @Test
    fun `should return 404 when getOriginalUrl is called with invalid shortId`() {
        // given
        val shortCode = "invalidId"

        given(urlShortenerService.getOriginalUrl(shortCode)).willReturn(null)

        // when and Then
        mockMvc.perform(get("/api/urls/$shortCode"))
            .andExpect(status().isNotFound)
    }
}
