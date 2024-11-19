package com.example.e_commerce_compose.di

import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.ProductRepository
import com.example.e_commerce_compose.repository.repositoryImpl.ProductRepositoryImpl
import com.example.e_commerce_compose.screens.mainScreen.MainScreenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGraphQLService(retrofit: Retrofit): GraphQLService {
        return retrofit.create(GraphQLService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        graphQLService: GraphQLService
    ): ProductRepository {
        return ProductRepositoryImpl(graphQLService)
    }

    @Provides
    @Singleton
    fun provideGetProductsByCategoryUseCase(
        repository: ProductRepository
    ): MainScreenUseCase {
        return MainScreenUseCase(repository)
    }
}