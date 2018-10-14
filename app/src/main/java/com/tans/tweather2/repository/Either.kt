package com.tans.tweather2.repository

sealed class Either<out L, out R>() {

    abstract fun isRight(): Boolean

    abstract fun isLeft(): Boolean

    class Right<T>(val data: T): Either<Nothing, T>() {

        override fun isRight(): Boolean = true

        override fun isLeft(): Boolean = false
    }

    class Left<T>(val data: T): Either<T, Nothing>() {
        override fun isRight(): Boolean = false

        override fun isLeft(): Boolean = true

    }

    companion object {
        fun <T> left(data: T) = Left(data)

        fun <T> right(data: T) = Right(data)
    }
}