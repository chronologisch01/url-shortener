package com.dkb_code_factory.url_shortener.model

import jakarta.persistence.*

@Entity
@Table(name = "url_mappings")
data class UrlMapping(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 2048)
    val originalUrl: String,

    @Column(nullable = false, unique = true)
    val shortCode: String
)