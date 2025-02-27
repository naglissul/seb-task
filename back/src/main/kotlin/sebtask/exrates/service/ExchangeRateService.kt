package sebtask.exrates.service

import sebtask.exrates.dto.CurrencyHistoryDay
import sebtask.exrates.dto.ExchangeDay
import sebtask.exrates.dto.ExchangeRate

interface ExchangeRateService {
    fun fetchTodaysRates(): List<ExchangeRate>

    fun fetchRatesHistory(): List<ExchangeDay>

    fun fetchOneCurrencyHistory(currency: String): List<CurrencyHistoryDay>
}
