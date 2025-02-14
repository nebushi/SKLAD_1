package com.example.myapplication22.domain

import java.util.UUID

class Store(
    var name: String,
    var address: String,
    var id: UUID = UUID.randomUUID(),
)