package com.dkb_code_factory.url_shortener.service

import com.dkb_code_factory.url_shortener.model.UrlMapping
import com.dkb_code_factory.url_shortener.repository.UrlMappingRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UrlMappingService(private val urlMappingRepository: UrlMappingRepository) {

    fun createShortUrl(originalUrl: String): String {
        val mapping = UrlMapping(originalUrl = originalUrl, shortCode = UUID.randomUUID().toString())
        urlMappingRepository.save(mapping)
        return mapping.shortCode
    }

    fun getOriginalUrl(shortCode: String): String? {
        val mapping = urlMappingRepository.findByShortCode(shortCode)
        return mapping?.originalUrl
    }


}