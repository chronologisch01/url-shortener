package com.dkb_code_factory.url_shortener.service

import com.dkb_code_factory.url_shortener.helper.AlphaNumericIdCreator.generateAlphanumericId
import com.dkb_code_factory.url_shortener.model.UrlMapping
import com.dkb_code_factory.url_shortener.repository.UrlShortenerRepository
import org.springframework.stereotype.Service

@Service
class UrlShortenerService(private val urlShortenerRepository: UrlShortenerRepository) {

    fun createShortUrl(originalUrl: String): String {
        val mapping = UrlMapping(originalUrl = originalUrl, shortCode = generateAlphanumericId(8))
        urlShortenerRepository.save(mapping)
        return mapping.shortCode
    }

    fun getOriginalUrl(shortCode: String): String? {
        val mapping = urlShortenerRepository.findByShortCode(shortCode)
        return mapping?.originalUrl
    }
}
