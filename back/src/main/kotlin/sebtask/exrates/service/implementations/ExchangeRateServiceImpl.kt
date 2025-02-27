package sebtask.exrates.service.implementations

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import sebtask.exrates.dto.ExchangeDay
import sebtask.exrates.dto.ExchangeRate
import sebtask.exrates.service.ExchangeDataFetcher
import sebtask.exrates.service.ExchangeRateService
import javax.xml.parsers.DocumentBuilderFactory

@Service
class ExchangeRateServiceImpl(
    private val exchangeDataFetcher: ExchangeDataFetcher,
    @Value("\${webfetcher.target-exchange-xml-url}") private val exchangeXmlUrl: String,
    @Value("\${webfetcher.exchange-history-xml-url}") private val exchangeHistoryXmlUrl: String,
) : ExchangeRateService {
    override fun fetchTodaysRates(): List<ExchangeRate> {
        val xmlData = exchangeDataFetcher.fetchXmlData(exchangeXmlUrl)
        return parseTodaysXmlData(xmlData)
    }

    override fun fetchRatesHistory(): List<ExchangeDay> {
        val xmlData = exchangeDataFetcher.fetchXmlData(exchangeHistoryXmlUrl)
        return parseHistoryXmlData(xmlData)
    }

    private fun parseTodaysXmlData(xmlData: String): List<ExchangeRate> {
        // Note: Not using XMLMapper, since it triggers global xml usage instead of json
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlInput = documentBuilder.parse(xmlData.byteInputStream())
        xmlInput.documentElement.normalize()

        val exchangeRates = mutableListOf<ExchangeRate>()
        val nodes = xmlInput.getElementsByTagName("Cube")

        for (i in 0 until nodes.length) {
            val element = nodes.item(i) as? Element
            val currency = element?.getAttribute("currency")
            val rate = element?.getAttribute("rate")?.toDoubleOrNull()

            if (currency != null && rate != null) {
                exchangeRates.add(ExchangeRate(currency, rate))
            }
        }
        return exchangeRates
    }

    private fun parseHistoryXmlData(xmlData: String): List<ExchangeDay> {
        // Note: Not using XMLMapper, since it triggers global xml usage instead of json
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlInput: Document = documentBuilder.parse(xmlData.byteInputStream())
        xmlInput.documentElement.normalize()

        val exchangeDays = mutableListOf<ExchangeDay>()

        // Get all <Cube> elements
        val allCubes = xmlInput.getElementsByTagName("Cube")

        for (i in 0 until allCubes.length) {
            val dateElement = allCubes.item(i) as? Element ?: continue

            // Only process <Cube> elements that have a "time" attribute (dates)
            val date = dateElement.getAttribute("time")
            if (date.isNullOrEmpty()) continue

            val rates = mutableListOf<ExchangeRate>()

            // Get all direct child <Cube> elements of this date element
            val rateNodes = dateElement.getElementsByTagName("Cube")
            for (j in 0 until rateNodes.length) {
                val rateElement = rateNodes.item(j) as? Element ?: continue
                val currency = rateElement.getAttribute("currency")
                val rate = rateElement.getAttribute("rate").toDoubleOrNull()

                if (!currency.isNullOrEmpty() && rate != null) {
                    rates.add(ExchangeRate(currency, rate))
                }
            }

            exchangeDays.add(ExchangeDay(date, rates))
        }

        return exchangeDays
    }
}
