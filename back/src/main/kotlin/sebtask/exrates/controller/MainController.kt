package sebtask.exrates.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sebtask.exrates.dto.CurrencyHistoryDay
import sebtask.exrates.dto.ExchangeDay
import sebtask.exrates.dto.ExchangeRate
import sebtask.exrates.service.ExchangeRateService

@RestController
@RequestMapping("/main")
class MainController {
    @Autowired
    private lateinit var exchangeRateService: ExchangeRateService

    @GetMapping("/todaysRates", produces = ["application/json"])
    fun getTodaysRates(): ResponseEntity<List<ExchangeRate>> = ResponseEntity.ok(exchangeRateService.fetchTodaysRates())

    @GetMapping("/ratesHistory", produces = ["application/json"])
    fun getRatesHistory(): ResponseEntity<List<ExchangeDay>> = ResponseEntity.ok(exchangeRateService.fetchRatesHistory())

    @GetMapping("/oneCurrencyHistory/{currency}", produces = ["application/json"])
    fun getOneCurrencyHistory(
        @PathVariable currency: String,
    ): ResponseEntity<List<CurrencyHistoryDay>> = ResponseEntity.ok(exchangeRateService.fetchOneCurrencyHistory(currency))
}
