package com.yuliakazachok.workmanager.domain.repository

interface FileRepository {

    fun create(data: String)

    fun delete()

    fun cancelCreating()

    fun updatePeriodic()
}