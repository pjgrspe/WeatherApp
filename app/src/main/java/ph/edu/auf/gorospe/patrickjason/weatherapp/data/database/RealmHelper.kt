package ph.edu.auf.gorospe.patrickjason.weatherapp.data.providers.realm

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import ph.edu.auf.gorospe.patrickjason.weatherapp.data.database.realmmodel.WeatherRealm

class RealmHelper {

    companion object {
        suspend fun saveFavoriteCity(city: WeatherRealm) {
            val instance = RealmHelper()
            instance.saveCity(city)
        }
    }

    private val config = RealmConfiguration.Builder(
        schema = setOf(WeatherRealm::class)
    )
        .schemaVersion(1)
        .deleteRealmIfMigrationNeeded()
        .build()

    private val realm: Realm = Realm.open(config)

    suspend fun saveCity(city: WeatherRealm) {
        realm.write {
            copyToRealm(city, updatePolicy = io.realm.kotlin.UpdatePolicy.ALL)
        }
    }

    fun getCities(): List<WeatherRealm> {
        return realm.query<WeatherRealm>().find()
    }

    fun getCityByName(cityName: String): WeatherRealm? {
        return realm.query<WeatherRealm>("name = $0", cityName).first().find()
    }

    suspend fun updateCityFavoriteStatus(cityName: String, isFavorite: Boolean) {
        realm.write {
            val city = query<WeatherRealm>("name = $0", cityName).first().find()
            city?.isFavorite = isFavorite
        }
    }

    suspend fun deleteCity(cityName: String) {
        realm.write {
            val city = query<WeatherRealm>("name = $0", cityName).first().find()
            if (city != null) {
                delete(city)
            }
        }
    }

    fun getFavoriteCities(): List<WeatherRealm> {
        return realm.query<WeatherRealm>("isFavorite = true").find()
    }

    fun close() {
        realm.close()
    }
}