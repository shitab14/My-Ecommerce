package com.mir.myecommerce.domain

import com.mir.myecommerce.data.datamodel.ResponseData
import com.mir.myecommerce.data.repository.apprepository.AppDataRepository
import com.mir.myecommerce.domain.appusecases.AppUseCase
import com.mir.myecommerce.network.ErrorMessage
import com.mir.myecommerce.network.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * Created by Shitab Mir on 31/5/24.
 * shitabmir@gmail.com
 **/
@ExperimentalCoroutinesApi
class AppUseCaseTest {

    private lateinit var repository: AppDataRepository
    private lateinit var appUseCase: AppUseCase

    @Before
    fun setUp() {
        // Initialize the mocked repository and the use case before each test
        repository = mock()
        appUseCase = AppUseCase(repository)
    }

    @Test
    fun `invoke should emit Loading and Success when response is successful`() = runTest {
        // Happy Path: When the response is successful, the use case should emit Loading and then Success state

        // Mock the token and response data
        val mockToken = "mockToken"
        val mockResponseData = ResponseData(id = 0, data = "le Data")
        val mockResponse = Response.success(mockResponseData)

        // Define the behavior of repository methods
        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Success with the correct data
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Success<*>)
        val successState = result[1] as State.Success<*>
        assertEquals(successState.data, mockResponseData)
    }

    @Test
    fun `invoke should emit Loading and Failed when response is unsuccessful`() = runTest {
        // Scenario: When the API response is unsuccessful, the use case should emit Loading and then Failed state

        // Mock the token and error response
        val mockToken = "mockToken"
        val mockResponse = Response.error<ResponseData>(401, mock())

        // Define the behavior of repository methods
        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Failed with the correct error code and message
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.code, 401)
        assertEquals(errorState.message, ErrorMessage.UNAUTHORIZED_USER)
    }

    @Test
    fun `invoke should emit Failed when repository throws an exception`() = runTest {
        // Scenario: When the repository throws an exception, the use case should emit Loading and then Failed state

        // Define the behavior of repository methods to throw an exception
        whenever(repository.getSomething()).thenThrow(RuntimeException::class.java)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Failed with the correct error message
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }

    @Test
    fun `invoke should emit Loading and Failed when response body is null`() = runTest {
        // Scenario: When the response body is null, the use case should emit Loading and then Failed state

        // Mock the token and null response body
        val mockToken = "mockToken"
        val mockResponse = Response.success<ResponseData>(null)

        // Define the behavior of repository methods
        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Failed with the correct error message
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }

    @Test
    fun `invoke should emit Failed when token is null`() = runTest {
        // Scenario: When the token is null, the use case should emit Loading and then Failed state

        // Define the behavior of repository methods to return null token
        whenever(repository.getSomething()).thenReturn(null)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Failed with the correct error message
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.UNAUTHORIZED_USER)
    }

    @Test
    fun `invoke should emit Loading and Failed when non-401 error occurs`() = runTest {
        // Scenario: When a non-401 error occurs, the use case should emit Loading and then Failed state

        // Mock the token and a non-401 error response
        val mockToken = "mockToken"
        val mockResponse = Response.error<ResponseData>(500, mock())

        // Define the behavior of repository methods
        whenever(repository.getSomething()).thenReturn(mockToken)
        whenever(repository.getSomethingIncoming(any())).thenReturn(mockResponse)

        // Collect the results emitted by the use case
        val result = appUseCase("testId").toList()

        // Assert that the first state is Loading and the second state is Failed with the correct error message
        assertEquals(result[0], State.Loading)
        assert(result[1] is State.Failed)
        val errorState = result[1] as State.Failed
        assertEquals(errorState.message, ErrorMessage.SOMETHING_WENT_WRONG)
    }
}
