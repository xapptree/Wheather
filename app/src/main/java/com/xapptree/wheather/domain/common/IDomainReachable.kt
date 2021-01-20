package com.xapptree.wheather.domain.common

interface IDomainReachable {
    fun onDomainReachableError(errorCode: Int)
}