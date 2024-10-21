package com.dkb_code_factory.url_shortener.repository

import com.dkb_code_factory.url_shortener.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlMappingRepository : JpaRepository<UrlMapping, Long> {
    fun findByShortCode(shortCode: String): UrlMapping?
    fun findByOriginalUrl(originalUrl: String): UrlMapping?
}
