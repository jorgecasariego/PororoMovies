package me.jorgecasariego.pororopeliculas.repository.config.datasource

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import me.jorgecasariego.pororopeliculas.base.Constants
import me.jorgecasariego.pororopeliculas.base.Constants.PREFERENCES_USER_INFO
import me.jorgecasariego.pororopeliculas.base.Constants.PREFERENCES_USER_INFORMATION
import me.jorgecasariego.pororopeliculas.model.UserInformation

class LocalConfigDataSource(private val applicationContext: Context) : ILocalConfigDataSource {

    private val sharedPreferences = applicationContext.getSharedPreferences(
            Constants.PREFERENCES_PORORO_NAME,
            Context.MODE_PRIVATE
    )

    private fun getCurrentUserLogged(): String? =
            sharedPreferences.getString(
                    PREFERENCES_USER_INFORMATION, ""
            )

    private fun getUserPreferences(): SharedPreferences ? {
        val currentUser = getCurrentUserLogged() ?: return null
        return applicationContext.getSharedPreferences(
                "pororo_$currentUser",
                Context.MODE_PRIVATE
        )
    }

    private fun setNewUser(username: String) {
        sharedPreferences.edit().putString(PREFERENCES_USER_INFORMATION, username).apply()
    }

    override fun setUserInfo(userInfo: UserInformation) {
        setNewUser(userInfo.username)

        val userPreferences = getUserPreferences() ?: return
        with(userPreferences.edit()) {
            putString(PREFERENCES_USER_INFO, Gson().toJson(userInfo))
            commit()
        }
    }

    override fun getCurrentUserInfo(): UserInformation? {
        val userPreferences = getUserPreferences() ?: return null
        val userInfoString = userPreferences.getString(PREFERENCES_USER_INFO, null)
        userInfoString ?: return null

        return Gson().fromJson(userInfoString, UserInformation::class.java)
    }
    
}