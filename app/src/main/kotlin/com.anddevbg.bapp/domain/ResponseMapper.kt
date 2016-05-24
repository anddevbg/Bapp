package com.anddevbg.bapp.domain

/**
 * Created by teodorpenkov on 5/23/16.
 */
interface ResponseMapper<P, T> {

    fun map(param: P): T
}