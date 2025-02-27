package sebtask.exrates.service

interface ExchangeDataFetcher {
    fun fetchXmlData(url: String): String
}
