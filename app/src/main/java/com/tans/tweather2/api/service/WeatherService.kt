package com.tans.tweather2.api.service

interface WeatherService {



    companion object {

        enum class WeatherQueryType(val value: String) {
            WIND("wind"),
            CONDITION("condition"),
            ATMOSPHERE("atmosphere"),
            FORECAST("item.forecast")
        }

        fun createYql(type: WeatherQueryType, city: String): String = """
            select ${type.value} from weather.forecast
            where ${ if (type == WeatherQueryType.CONDITION || type == WeatherQueryType.FORECAST) "u=\"c\" and" else "" }
            woeid in (select weoid from geo.places(1) where text=$city)
        """
    }
}