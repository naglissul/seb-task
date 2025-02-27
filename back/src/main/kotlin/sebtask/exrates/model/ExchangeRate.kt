package sebtask.exrates.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "exchange_rates")
data class ExchangeRate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val date: LocalDate,
    @Column(nullable = false)
    val currency: String,
    @Column(nullable = false)
    val rate: Double,
)
