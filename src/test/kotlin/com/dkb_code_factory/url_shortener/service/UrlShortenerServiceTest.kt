package com.dkb_code_factory.url_shortener.service

import com.dkb_code_factory.url_shortener.helper.AlphaNumericIdCreator
import com.dkb_code_factory.url_shortener.model.UrlMapping
import com.dkb_code_factory.url_shortener.repository.UrlShortenerRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UrlShortenerServiceTest {

    @MockK
    private lateinit var urlShortenerRepository: UrlShortenerRepository

    @InjectMockKs
    private lateinit var urlShortenerService: UrlShortenerService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockkObject(AlphaNumericIdCreator)
    }

    @Test
    fun `test createShortUrl`() {
        // given
        val originalUrl = "http://example.com"
        val shortCode = "abc12345"
        val returnedMapping = UrlMapping(1235L, originalUrl, shortCode)
        every { AlphaNumericIdCreator.generateAlphanumericId(8) } returns shortCode
        every { urlShortenerRepository.save(any()) } returns returnedMapping

        // when
        val result = urlShortenerService.createShortUrl(originalUrl)

        // then
        val mappingSlot = slot<UrlMapping>()
        verify { urlShortenerRepository.save(capture(mappingSlot)) }
        assertEquals(shortCode, result)
        assertEquals(originalUrl, mappingSlot.captured.originalUrl)
        assertEquals(shortCode, mappingSlot.captured.shortCode)
    }

    @Test
    fun `test getOriginalUrl when mapping exists`() {
        val shortCode = "abc12345"
        val originalUrl = "http://example.com"

        val mapping = UrlMapping(originalUrl = originalUrl, shortCode = shortCode)

        every { urlShortenerRepository.findByShortCode(shortCode) } returns mapping

        val result = urlShortenerService.getOriginalUrl(shortCode)

        assertEquals(originalUrl, result)
    }

    @Test
    fun `test getOriginalUrl when mapping does not exist`() {
        val shortCode = "nonexistent"

        every { urlShortenerRepository.findByShortCode(shortCode) } returns null

        val result = urlShortenerService.getOriginalUrl(shortCode)

        assertNull(result)
    }
}
