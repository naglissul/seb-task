package sebtask.exrates.repository

import org.springframework.data.jpa.repository.JpaRepository
import sebtask.exrates.model.ExchangeRate
import java.time.LocalDate

interface ExchangeRateRepository : JpaRepository<ExchangeRate, Long> {
    fun findByDate(date: LocalDate): List<ExchangeRate>

    fun findByCurrency(currency: String): List<ExchangeRate>

    fun findByCurrencyAndDate(
        currency: String,
        date: LocalDate,
    ): List<ExchangeRate>
}
