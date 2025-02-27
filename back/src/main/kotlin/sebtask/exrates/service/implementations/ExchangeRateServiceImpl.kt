package sebtask.exrates.service.implementations

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import sebtask.exrates.dto.CurrencyHistoryDayDto
import sebtask.exrates.dto.ExchangeDayDto
import sebtask.exrates.dto.ExchangeRateDto
import sebtask.exrates.model.ExchangeRate
import sebtask.exrates.repository.ExchangeRateRepository
import sebtask.exrates.service.ExchangeDataFetcher
import sebtask.exrates.service.ExchangeRateService
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

@Service
class ExchangeRateServiceImpl(
    private val exchangeDataFetcher: ExchangeDataFetcher,
    private val exchangeRateRepository: ExchangeRateRepository,
    @Value("\${webfetcher.target-exchange-xml-url}") private val exchangeXmlUrl: String,
    @Value("\${webfetcher.exchange-history-xml-url}") private val exchangeHistoryXmlUrl: String,
) : ExchangeRateService {
    override fun fetchTodaysRates(): List<ExchangeRateDto> {
        val todayDate = getTodaysDate()
        val rates = exchangeRateRepository.findByDate(LocalDate.parse(todayDate))
        if (rates.isNotEmpty()) {
            return rates.map { ExchangeRateDto(it.currency, it.rate) }
        } else {
            val xmlData = exchangeDataFetcher.fetchXmlData(exchangeXmlUrl)
            val exchangeRateDtos = parseTodaysXmlData(xmlData)
            exchangeRateRepository.saveAll(
                exchangeRateDtos.map {
                    ExchangeRate(currency = it.currency, rate = it.rate, date = LocalDate.parse(todayDate))
                },
            )
            return exchangeRateDtos.map { ExchangeRateDto(it.currency, it.rate) }
        }
    }

    @PostConstruct
    override fun fetchRatesHistory(): List<ExchangeDayDto> {
        val rates = exchangeRateRepository.findAll()
        if (rates.isNotEmpty()) {
            val exchangeDayDtos =
                rates.groupBy { it.date }.map { (date, rates) ->
                    ExchangeDayDto(date.toString(), rates.map { ExchangeRateDto(it.currency, it.rate) })
                }
            return exchangeDayDtos
        } else {
            val xmlData = exchangeDataFetcher.fetchXmlData(exchangeHistoryXmlUrl)
            val exchangeDayDtos = parseHistoryXmlData(xmlData)
            exchangeRateRepository.saveAll(
                exchangeDayDtos.flatMap { exchangeDayDto ->
                    exchangeDayDto.rates.map {
                        ExchangeRate(currency = it.currency, rate = it.rate, date = LocalDate.parse(exchangeDayDto.date))
                    }
                },
            )
            return exchangeDayDtos
        }
    }

    override fun fetchOneCurrencyHistory(currency: String): List<CurrencyHistoryDayDto> {
        val rates = exchangeRateRepository.findByCurrency(currency)
        if (rates.isNotEmpty()) {
            return rates.groupBy { it.date }.map { (date, rates) ->
                CurrencyHistoryDayDto(date.toString(), rates.first { it.currency == currency }.rate)
            }
        } else {
            val rates2 = fetchRatesHistory().flatMap { it.rates }
            val currencyRates = rates2.filter { it.currency == currency }
            return currencyRates
                .groupBy { it.currency }
                .map { rates.map { CurrencyHistoryDayDto(it.date.toString(), it.rate) } }
                .flatten()
        }
    }

    private fun parseTodaysXmlData(xmlData: String): List<ExchangeRateDto> {
        // Note: Not using XMLMapper, since it triggers global xml usage instead of json
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlInput = documentBuilder.parse(xmlData.byteInputStream())
        xmlInput.documentElement.normalize()

        val exchangeRateDtos = mutableListOf<ExchangeRateDto>()
        val nodes = xmlInput.getElementsByTagName("Cube")

        for (i in 0 until nodes.length) {
            val element = nodes.item(i) as? Element
            val currency = element?.getAttribute("currency")
            val rate = element?.getAttribute("rate")?.toDoubleOrNull()

            if (currency != null && rate != null) {
                exchangeRateDtos.add(ExchangeRateDto(currency, rate))
            }
        }
        return exchangeRateDtos
    }

    private fun parseHistoryXmlData(xmlData: String): List<ExchangeDayDto> {
        // Note: Not using XMLMapper, since it triggers global xml usage instead of json
        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val xmlInput: Document = documentBuilder.parse(xmlData.byteInputStream())
        xmlInput.documentElement.normalize()

        val exchangeDayDtos = mutableListOf<ExchangeDayDto>()

        // Get all <Cube> elements
        val allCubes = xmlInput.getElementsByTagName("Cube")

        for (i in 0 until allCubes.length) {
            val dateElement = allCubes.item(i) as? Element ?: continue

            // Only process <Cube> elements that have a "time" attribute (dates)
            val date = dateElement.getAttribute("time")
            if (date.isNullOrEmpty()) continue

            val rates = mutableListOf<ExchangeRateDto>()

            // Get all direct child <Cube> elements of this date element
            val rateNodes = dateElement.getElementsByTagName("Cube")
            for (j in 0 until rateNodes.length) {
                val rateElement = rateNodes.item(j) as? Element ?: continue
                val currency = rateElement.getAttribute("currency")
                val rate = rateElement.getAttribute("rate").toDoubleOrNull()

                if (!currency.isNullOrEmpty() && rate != null) {
                    rates.add(ExchangeRateDto(currency, rate))
                }
            }

            exchangeDayDtos.add(ExchangeDayDto(date, rates))
        }

        return exchangeDayDtos
    }

    private fun getTodaysDate(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return today.format(formatter)
    }
}
