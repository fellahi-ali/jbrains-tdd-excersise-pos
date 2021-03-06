package dev.codingart.learning.tdd.pos

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BarcodeAsValueObject {
    private val display = Display()
    private val catalog = Catalog()
    private val register = Register(display, catalog)

    @Test
    fun `barcode found`() {
        val barcode = Barcode("12345")
        catalog.addProduct(barcode, "$10.00")
        register.onBarCode(barcode)
        display.message shouldBe "$10.00"
    }

    @Test
    fun `another barcode found`() {
        val barcode = Barcode("23456")
        catalog.addProduct(barcode, "$5.00")
        register.onBarCode(barcode)
        display.message shouldBe "$5.00"
    }

    @Test
    fun `product not found`() {
        val barcode = Barcode("00000")
        register.onBarCode(barcode)
        display.message shouldBe "Barcode 00000 not found"
    }


    @ParameterizedTest
    @ValueSource(strings = ["","      ", " \n ", " \t "])
    fun `blank barcode`(blankStrings: String) {
        val ex = shouldThrow<IllegalArgumentException> { Barcode(blankStrings) }
        ex.message shouldBe "Barcode cannot be blank"
    }
}