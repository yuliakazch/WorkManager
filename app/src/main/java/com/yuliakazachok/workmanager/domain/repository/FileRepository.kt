package com.yuliakazachok.workmanager.domain.repository

interface FileRepository {

    suspend fun create(data: String)

    suspend fun delete()
}