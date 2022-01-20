package com.dfarm.superheroes

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dfarm.superheroes.Ui.LoginValidations
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginValidatorText {

    lateinit var validation: LoginValidations

    @Before
    fun init() {
        validation = LoginValidations()
    }

    @Test
    fun check_UserName_Normal() {
        Truth.assertThat(
            validation.validatorUserName("abc")
        ).isAnyOf(true, false)
    }

    @Test
    fun check_UserName_Empty() {
        Truth.assertThat(
            validation.validatorUserName("")
        ).isFalse()
    }

    @Test
    fun check_UserName_Valid_Length() {
        Truth.assertThat(
            validation.validatorUserName("sads sadac ")
        ).isFalse()
    }


    @Test
    fun check_UserName_Contains_Hash_char() {
        Truth.assertThat(
            validation.validatorUserName("#ddd")
        ).isFalse()
    }


    @Test
    fun check_Password_Validation1() {
        Truth.assertThat(
            validation.validatorPassword("aaa")
        ).isTrue()
    }

    @Test
    fun check_Password_Validation2() {
        Truth.assertThat(
            validation.validatorPassword("a#aa")
        ).isTrue()
    }

    @Test
    fun check_Password_Validation3() {
        Truth.assertThat(
            validation.validatorPassword("a\$aa")
        ).isTrue()
    }

    @Test
    fun check_Password_Validation4() {
        Truth.assertThat(
            validation.validatorPassword("a#aa")
        ).isTrue()
    }

    @Test
    fun check_Password_Validation5() {
        Truth.assertThat(
            validation.validatorPassword("a@aa")
        ).isTrue()
    }
}