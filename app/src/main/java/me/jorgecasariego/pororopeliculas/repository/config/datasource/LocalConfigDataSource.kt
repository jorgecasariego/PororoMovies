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

    /**
     * PREFERENCES_USER_INFORMATION store the current user logged to the app
     */
    private fun setCurrentLoggedUser(username: String) {
        sharedPreferences.edit().putString(PREFERENCES_USER_INFORMATION, username).apply()
    }

    /**
     * get the current logged user to the app using PREFERENCES_USER_INFORMATION field
     */
    private fun getCurrentUserLogged(): String? =
            sharedPreferences.getString(
                    PREFERENCES_USER_INFORMATION, ""
            )

    /**
     * For every new user register to the app we'll get a specific SP. This method
     * return the SP associated to the user if exists else return null
     */
    private fun getUserPreferences(): SharedPreferences ? {
        val currentUser = getCurrentUserLogged() ?: return null
        return applicationContext.getSharedPreferences(
                "pororo_$currentUser",
                Context.MODE_PRIVATE
        )
    }

    /**
     * Try to get the Shared Preferences associated to the email
     */
    private fun getUserPreferencesByEmail(email: String): SharedPreferences ? {
        return applicationContext.getSharedPreferences(
                "pororo_$email",
                Context.MODE_PRIVATE
        )
    }

    /**
     * This method set the current logged user and in the specifi SP of the user set the
     * user information.
     */
    override fun setUserInfo(userInfo: UserInformation) {
        setCurrentLoggedUser(userInfo.email)

        val userPreferences = getUserPreferences() ?: return
        with(userPreferences.edit()) {
            putString(PREFERENCES_USER_INFO, Gson().toJson(userInfo))
            commit()
        }
    }

    /**
     * This method return the UserInformation related to the user logged in the app in this moment.
     */
    override fun getCurrentUserInfo(): UserInformation? {
        val userPreferences = getUserPreferences() ?: return null
        val userInfoString = userPreferences.getString(PREFERENCES_USER_INFO, null)
        userInfoString ?: return null

        return Gson().fromJson(userInfoString, UserInformation::class.java)
    }

    /**
     * This method return the UserInformation if the email exists in SP
     */
    override fun getCurrentUserInfoByEmail(email: String): UserInformation? {
        val userPreferences = getUserPreferencesByEmail(email) ?: return null
        val userInfoString = userPreferences.getString(PREFERENCES_USER_INFO, null)
        userInfoString ?: return null

        return Gson().fromJson(userInfoString, UserInformation::class.java)
    }
    
}