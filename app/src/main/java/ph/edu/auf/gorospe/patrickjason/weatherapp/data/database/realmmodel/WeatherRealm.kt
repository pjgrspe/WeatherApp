package ph.edu.auf.gorospe.patrickjason.weatherapp.data.database.realmmodel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class WeatherRealm : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""  // This is the city name
    var temperature: Double = 0.0
    var description: String = ""
    var feelsLike: Double = 0.0
    var humidity: Int = 0
    var windSpeed: Double = 0.0
    var icon: String = ""
    var isFavorite: Boolean = false
}

class HourlyForecastRealm : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var cityId: Int = 0
    var dateTime: Long = 0
    var temperature: Double = 0.0
    var icon: String = ""
}