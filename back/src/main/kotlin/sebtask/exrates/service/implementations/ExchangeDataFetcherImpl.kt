package sebtask.exrates.service.implementations

import org.springframework.stereotype.Service
import sebtask.exrates.service.ExchangeDataFetcher
import java.net.URI

@Service
class ExchangeDataFetcherImpl : ExchangeDataFetcher {
    override fun fetchXmlData(url: String): String = URI(url).toURL().readText()
}
