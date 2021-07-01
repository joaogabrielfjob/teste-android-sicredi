package br.com.testesicredi

import br.com.testesicredi.util.Util
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun test_validation() {
        val util = Util()
        val nameValid = "Carlos"
        val nameInvalid = "123Joseph"

        assertEquals(util.isNameInvalid(nameValid), false)
        assertEquals(util.isNameInvalid(nameInvalid), true)
    }

    @Test
    fun email_validation() {
        val util = Util()
        val emailValid = "teste@teste.com"
        val emailInvalid = "123ASds.com"

        assertEquals(util.isEmailInvalid(emailValid), false)
        assertEquals(util.isEmailInvalid(emailInvalid), true)
    }
}