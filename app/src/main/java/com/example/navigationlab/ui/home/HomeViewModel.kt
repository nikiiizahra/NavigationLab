package com.example.navigationlab.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigationlab.data.preferences.UserPreferencesRepository
import com.example.navigationlab.data.repository.StudentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: StudentRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeSearch()
        observeSearchPersistence()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    fetchStudents(query, fallbackError = "Pencarian gagal")
                }
        }
    }
    @OptIn(FlowPreview::class)
    private fun observeSearchPersistence() {
        viewModelScope.launch {
            // 1) Pulihkan query terakhir
            _searchQuery.value = preferencesRepository.lastSearchQuery.first()

            // 2) Simpan setiap perubahan (ditunda 500 ms)
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { value ->
                    preferencesRepository.saveLastSearchQuery(value)
                }
        }
    }

    fun loadStudents() {
        val query = searchQuery.value
        viewModelScope.launch {
            fetchStudents(query, fallbackError = "Request gagal")
        }
    }

    fun refresh() {
        val query = searchQuery.value
        viewModelScope.launch {
            fetchStudents(query, fallbackError = "Refresh gagal")
        }
    }

    private suspend fun fetchStudents(query: String, fallbackError: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        try {
            val data = if (query.isBlank()) {
                repository.getStudents()
            } else {
                repository.searchStudents(query)
            }
            _uiState.value = HomeUiState(students = data)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _uiState.value = HomeUiState(
                errorMessage = e.localizedMessage ?: fallbackError
            )
        }
    }

    companion object {
        fun factory(
            repository: StudentRepository,
            preferencesRepository: UserPreferencesRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository, preferencesRepository) as T
            }
        }
    }
}
