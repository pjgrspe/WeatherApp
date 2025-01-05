package ph.edu.auf.gorospe.patrickjason.weatherapp.api.models.hourly

import com.google.gson.annotations.SerializedName

data class HourlyModel(
    @SerializedName("city")
    val city: HourlyCity,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<HourlyItem0>,
    @SerializedName("message")
    val message: Int
)

data class HourlyCity(
    @SerializedName("coord")
    val coord: HourlyCoord,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
)

data class HourlyClouds(
    @SerializedName("all")
    val all: Int
)

data class HourlyCoord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

data class HourlyItem0(
    @SerializedName("clouds")
    val clouds: HourlyClouds,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("main")
    val main: HourlyMain,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: HourlyRain,
    @SerializedName("sys")
    val sys: HourlySys,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<HourlyWeather>,
    @SerializedName("wind")
    val wind: HourlyWind
)

data class HourlyMain(
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_kf")
    val tempKf: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

data class HourlyRain(
    @SerializedName("1h")
    val h: Double
)

data class HourlySys(
    @SerializedName("pod")
    val pod: String
)

data class HourlyWeather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)

data class HourlyWind(
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("speed")
    val speed: Double
)