package com.example.myapplication22.domain

import android.content.ClipData
import java.util.UUID

class StoredItem(
    var store: Store,
    var item: Item,
    var place: String,
    var id: UUID = UUID.randomUUID(),
)