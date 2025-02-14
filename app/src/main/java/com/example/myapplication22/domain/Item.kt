package com.example.myapplication22.domain

import java.util.UUID

class Item(
    var name: String,
    var description: String,
    var id: UUID = UUID.randomUUID(),
)