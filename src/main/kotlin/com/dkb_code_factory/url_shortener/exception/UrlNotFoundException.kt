package com.dkb_code_factory.url_shortener.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UrlNotFoundException(message: String) : RuntimeException(message)
