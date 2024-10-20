package com.dkb_code_factory.url_shortener.controller

import com.dkb_code_factory.url_shortener.exception.UrlNotFoundException
import com.dkb_code_factory.url_shortener.model.dto.CreateUrlRequest
import com.dkb_code_factory.url_shortener.model.dto.CreateUrlResponse
import com.dkb_code_factory.url_shortener.model.dto.GetUrlResponse
import com.dkb_code_factory.url_shortener.service.UrlMappingService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/urls")
@Validated
class UrlMappingController(private val service: UrlMappingService) {

    @PostMapping
    fun createShortUrl(@RequestBody request: CreateUrlRequest): ResponseEntity<CreateUrlResponse> {
        val shortCode = service.createShortUrl(request.url)
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUrlResponse(shortCode))
    }

    @GetMapping("/{shortCode}")
    fun getOriginalUrl(@PathVariable shortCode: String): ResponseEntity<GetUrlResponse> {
        val originalUrl = service.getOriginalUrl(shortCode)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        return ResponseEntity.ok(GetUrlResponse(originalUrl))
    }

    @GetMapping("/r/{shortCode}")
    fun redirectToOriginalUrl(
        @PathVariable shortCode: String,
        response: HttpServletResponse
    ) {
        val originalUrl = service.getOriginalUrl(shortCode)
            ?: throw UrlNotFoundException("URL not found for code: $shortCode")
        response.sendRedirect(originalUrl)
    }
}

