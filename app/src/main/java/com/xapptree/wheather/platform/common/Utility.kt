package com.xapptree.wheather.platform.common

import com.xapptree.wheather.domain.common.IDomainReachable
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

object Utility {

    fun sendDomainError(callBack: Any?, throwable: Throwable? = null) {
        var errorCode = APIConstants.ERROR_CODE.EXCEPTION.ordinal
        if (throwable != null) {

            when (throwable) {
                is SSLHandshakeException -> {
                    errorCode = APIConstants.ERROR_CODE.SSL_HANDSHAKE_EXCEPTION.ordinal
                }
                is SocketTimeoutException -> {
                    errorCode = APIConstants.ERROR_CODE.TIMEOUT_EXCEPTION.ordinal
                }
                is IOException -> {
                    errorCode = APIConstants.ERROR_CODE.IO_EXCEPTION.ordinal
                }
            }
        }
        if (callBack != null && callBack is IDomainReachable) {
            callBack.onDomainReachableError(errorCode)
        }
    }
}