package sebtask.exrates.service

import sebtask.exrates.dto.CurrencyHistoryDayDto
import sebtask.exrates.dto.ExchangeDayDto
import sebtask.exrates.dto.ExchangeRateDto

interface ExchangeRateService {
    fun fetchTodaysRates(): List<ExchangeRateDto>

    fun fetchRatesHistory(): List<ExchangeDayDto>

    fun fetchOneCurrencyHistory(currency: String): List<CurrencyHistoryDayDto>
}
