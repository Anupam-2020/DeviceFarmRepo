package com.dfarm.superheroes.Ui

class LoginValidations {

    fun validatorUserName(urNm: String): Boolean {

        if(urNm.isEmpty()) return false
        if (urNm.length>=8) return false
        if(urNm.contains("#")) return false
        return true
    }

    fun validatorPassword(psWrd: String): Boolean {

        if(psWrd.isEmpty()) return false
        if(psWrd.contains(Regex("[a-zA-Z0-9#%$\\s]{0,19}"))) return true
        return false
    }
}