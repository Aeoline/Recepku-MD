package com.aubrey.recepku.tools

import com.google.gson.*
import java.lang.reflect.Type

class IsFavoriteDeserializer : JsonDeserializer<Boolean> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Boolean {
        return when {
            json.isJsonPrimitive -> json.asBoolean
            json.isJsonObject -> {
                val valueElement = json.asJsonObject.get("value")
                valueElement?.asBoolean ?: false  // Safely handle the case where 'value' is missing or null
            }
            else -> false
        }
    }
}

