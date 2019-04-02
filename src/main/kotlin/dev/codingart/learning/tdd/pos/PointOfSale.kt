package dev.codingart.learning.tdd.pos

class Display {
    var message = "Hello"

    // SMELL (primitive obsession): representing the price with string?!!!
    fun showPrice(price: String) {
        message = price
    }

    fun showBarcodeNotFoundMessage(barCode: String) {
        message = "Barcode $barCode not found"
    }

    fun showInvalidBarcodeMessage() {
        message = "Invalid Barcode"
    }
}

class Catalogue(private val priceByBarcode: MutableMap<String, String>) {
    // SMELL (primitive obsession): return the price as String
    fun findPrice(barCode: String) = priceByBarcode[barCode] //?: throw IllegalArgumentException("Barcode not found")
}

class SaleController(
    private val display: Display,
    private val catalogue: Catalogue
) {

    fun onBarCode(barCode: String) {
        // SMELL: A path that doesn't need a collaborator,
        // maybe this logic (barcode validation doesn't belong here)
        if (barCode.isBlank()) {
            display.showInvalidBarcodeMessage()
            return
        }

        val price = catalogue.findPrice(barCode)
        if (price != null) {
            display.showPrice(price)
        } else {
            display.showBarcodeNotFoundMessage(barCode)
        }
    }


}