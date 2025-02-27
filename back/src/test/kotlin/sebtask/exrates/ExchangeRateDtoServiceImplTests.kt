import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import sebtask.exrates.dto.ExchangeDayDto
import sebtask.exrates.dto.ExchangeRateDto
import sebtask.exrates.service.ExchangeDataFetcher
import sebtask.exrates.service.implementations.ExchangeRateServiceImpl

class ExchangeRateDtoServiceImplTests {
    private val mockFetcher = mockk<ExchangeDataFetcher>()
    private val service = ExchangeRateServiceImpl(mockFetcher, "dummy-url", "dummy-url")

    @Test
    fun `should correctly parse exchange rate XML`() {
        val xmlData =
            """
            <?xml version="1.0"?>
            <gesmes:Envelope>
                <Cube>
                    <Cube time="2024-02-26">
                        <Cube currency="USD" rate="1.085" />
                        <Cube currency="GBP" rate="0.862" />
                    </Cube>
                </Cube>
            </gesmes:Envelope>
            """.trimIndent()

        // Mock fetcher response instead of making a real HTTP request
        every { mockFetcher.fetchXmlData(any()) } returns xmlData

        val result = service.fetchRatesHistory()

        assertEquals(1, result.size)
        assertEquals("2024-02-26", result[0].date)
        assertEquals(2, result[0].rates.size)
        assertEquals("USD", result[0].rates[0].currency)
        assertEquals(1.085, result[0].rates[0].rate)
        assertEquals("GBP", result[0].rates[1].currency)
        assertEquals(0.862, result[0].rates[1].rate)
    }

    @Test
    fun `should correctly parse exchange rate history XML`() {
        val xmlData =
            """
            <?xml version="1.0"?>
            <gesmes:Envelope>
                <Cube>
                    <Cube time="2024-02-26">
                        <Cube currency="USD" rate="1.085" />
                        <Cube currency="GBP" rate="0.862" />
                    </Cube>
                    <Cube time="2024-02-25">
                        <Cube currency="USD" rate="1.083" />
                        <Cube currency="GBP" rate="0.860" />
                    </Cube>
                </Cube>
            </gesmes:Envelope>
            """.trimIndent()

        every { mockFetcher.fetchXmlData(any()) } returns xmlData

        val result: List<ExchangeDayDto> = service.fetchRatesHistory()

        assertEquals(2, result.size)

        assertEquals("2024-02-26", result[0].date)
        assertEquals(2, result[0].rates.size)
        assertEquals(ExchangeRateDto("USD", 1.085), result[0].rates[0])
        assertEquals(ExchangeRateDto("GBP", 0.862), result[0].rates[1])

        assertEquals("2024-02-25", result[1].date)
        assertEquals(2, result[1].rates.size)
        assertEquals(ExchangeRateDto("USD", 1.083), result[1].rates[0])
        assertEquals(ExchangeRateDto("GBP", 0.860), result[1].rates[1])
    }
}
