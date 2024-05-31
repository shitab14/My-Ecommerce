package com.mir.myecommerce.domain

import com.mir.myecommerce.data.datamodel.ResponseData
import com.mir.myecommerce.data.repository.apprepository.AppDataRepository
import com.mir.myecommerce.domain.appusecases.AppUseCase
import com.mir.myecommerce.network.ErrorMessage
import com.mir.myecommerce.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

/**
Created by Shitab Mir on 31/5/24.
shitabmir@gmail.com
 **/
@ExperimentalCoroutinesApi
class AppUseCaseTest {

    private lateinit var repository: AppDataRepository
    private lateinit var appUseCase: AppUseCase

    @Before
    fun setUp() {
        repository = mock()
        appUseCase = AppUseCase(repository)
    }

    @Test
    fun `invoke should emit Loading and Success when response is successful`() = runTest {
        val mockToken = "mockToken"
        val mockResponseData = ResponseData(
            id = 0,
            data = "le Data"
        )
        val mockResponse = Response.success(mockResponseData)

        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Success<*>)
        val successState = result[1] as State.Success<*>
        assertEquals(successState.data, mockResponseData)
    }

    @Test
    fun `invoke should emit Loading and Failed when response is unsuccessful`() = runTest {
        val mockToken = "mockToken"
        val mockResponse = Response.error<ResponseData>(401, mock())

        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.code, 401)
        assertEquals(errorState.message, ErrorMessage.UNAUTHORIZED_USER)
    }

    @Test
    fun `invoke should emit Failed when repository throws an exception`() = runTest {
        whenever(repository.getSomething()).thenThrow(RuntimeException::class.java)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }

    @Test
    fun `invoke should emit Loading and Failed when response body is null`() = runTest {
        val mockToken = "mockToken"
        val mockResponse = Response.success<ResponseData>(null)

        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }

    @Test
    fun `invoke should emit Failed when token is null`() = runTest {
        whenever(repository.getSomething()).thenReturn(null)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }

    @Test
    fun `invoke should emit Loading and Failed when non-401 error occurs`() = runTest {
        val mockToken = "mockToken"
        val mockResponse = Response.error<ResponseData>(500, mock())

        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        val result = appUseCase("testId").toList()

        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }
    // Additional tests for other scenarios can be added similarly.
}