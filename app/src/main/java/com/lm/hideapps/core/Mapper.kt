package com.lm.hideapps.core

interface Mapper {

    interface Data<D, U> : Mapper {
        fun map(request: D?): U
    }

    interface DataToUI<D, U> : Data<D?, U> {
        override fun map(request: D?): U
        fun map(throwable: Throwable): U
    }
}