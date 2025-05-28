package com.pjhdev.UserService.exception

object ErrorCodes {
    //=============================================================================================
    // 일반 에러
    //=============================================================================================
    const val GENERIC = 2000
    const val INVALID_REQUEST = 2001
    const val INVALID_ARGUMENT = 2002
    const val MISSING_REQUIRED_FIELD = 2003
    const val DB_ERROR = 2004
    const val INTERNAL_ERROR = 2005
    const val NOT_FOUND = 2006
    const val PERMISSION_DENIED = 2007
    const val DATA_ERROR = 2008
    const val DUPLICATED_DATA = 2009
    const val INTEGRITY_VIOLATION_ERROR = 2010

    //=============================================================================================
    // 인증 관련
    //=============================================================================================

    const val NEED_LOGIN = 2100
    const val BAD_CREDENTIALS_ERROR = 2101
    const val LOGIN_ERROR = 2102
}