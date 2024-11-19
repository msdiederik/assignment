package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/api/interest-rates'
        headers {
            contentType('application/json')
        }
    }
}
