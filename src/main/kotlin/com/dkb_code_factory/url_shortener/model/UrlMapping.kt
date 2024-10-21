package com.dkb_code_factory.url_shortener.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "url_mappings")
data class UrlMapping(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 2048)
    val originalUrl: String,

    @Column(nullable = false, unique = true)
    val shortCode: String
)
