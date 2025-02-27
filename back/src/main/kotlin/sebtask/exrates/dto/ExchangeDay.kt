package sebtask.exrates.dto

data class ExchangeDay(
    val date: String,
    val rates: List<ExchangeRate>,
)
