package com.example.e_commerce_compose.di

import com.apollographql.apollo3.ApolloClient
import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.CategoryRepository
import com.example.e_commerce_compose.repository.ProductDetailRepository
import com.example.e_commerce_compose.repository.ProductRepository
import com.example.e_commerce_compose.repository.repositoryImpl.CategoryRepositoryImpl
import com.example.e_commerce_compose.repository.repositoryImpl.ProductDetailRepositoryImpl
import com.example.e_commerce_compose.repository.repositoryImpl.ProductRepositoryImpl
import com.example.e_commerce_compose.screens.categoryScreen.CategoriesUseCase
import com.example.e_commerce_compose.screens.categoryScreen.CategoryUiMapper
import com.example.e_commerce_compose.screens.mainScreen.MainScreenUseCase
import com.example.e_commerce_compose.screens.productDetailScreen.ProductDetailUseCase
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiMapper
import com.example.e_commerce_compose.screens.productScreen.ProductsUseCase
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiMapper
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
        graphQLService: ApolloClient,
        uiMapper: ProductUiMapper
    ): ProductRepository {
        return ProductRepositoryImpl(graphQLService,uiMapper)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        graphQLService: ApolloClient,
        uiMapper: CategoryUiMapper
    ): CategoryRepository {
        return CategoryRepositoryImpl(graphQLService,uiMapper)
    }

    @Provides
    @Singleton
    fun provideProductDetailRepository(
        graphQLService: ApolloClient,
        uiMapper: ProductDetailUiMapper
    ): ProductDetailRepository {
        return ProductDetailRepositoryImpl(graphQLService,uiMapper)
    }

    @Provides
    @Singleton
    fun provideProductDetailUseCase(
        repository: ProductDetailRepository
    ): ProductDetailUseCase {
        return ProductDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProductsByCategoryUseCase(
        repository: ProductRepository
    ): MainScreenUseCase {
        return MainScreenUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(
        repository: CategoryRepository
    ): CategoriesUseCase {
        return CategoriesUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideProductUseCase(
        repository: ProductRepository
    ): ProductsUseCase {
        return ProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun productUiMapper(): ProductUiMapper {
        return ProductUiMapper()
    }

    @Provides
    @Singleton
    fun productCategoryUiMapper(): CategoryUiMapper {
        return CategoryUiMapper()
    }

    @Provides
    @Singleton
    fun productDetailUiMapper(): ProductDetailUiMapper {
        return ProductDetailUiMapper()
    }
}