package sebtask.exrates.dto

data class ExchangeDayDto(
    val date: String,
    val rates: List<ExchangeRateDto>,
)
