package org.rriv.android.utils


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OutputModel(
    @SerializedName("reading")
    val reading: Reading
) {
    @Keep
    data class Reading(
        @SerializedName("sensors")
        val sensors: List<Sensor>,
        @SerializedName("timestamp")
        val timestamp: Int
    ) {
        @Keep
        data class Sensor(
            @SerializedName("sensor")
            val sensor: Sensor
        ) {
            @Keep
            data class Sensor(
                @SerializedName("id")
                val id: String,
                @SerializedName("parameters")
                val parameters: List<Parameter>
            ) {
                @Keep
                data class Parameter(
                    @SerializedName("parameter")
                    val parameter: String,
                    @SerializedName("value")
                    val value: String
                )
            }
        }
    }
}