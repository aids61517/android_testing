package aids61517.unittest

import aids61517.unittest.data.Product
import aids61517.unittest.repository.ProductRepository
import aids61517.unittest.viewmodel.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.mockk.every
import io.mockk.mockk
import okio.buffer
import okio.source
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@RunWith(JUnitPlatform::class)
object MainViewModelTest : Spek({
  include(InstantTaskExecutorSpek)

  val groupBuyId = "3997"
  val repository: ProductRepository = mockk()

  lateinit var viewModel: MainViewModel
  lateinit var productList: List<Product>

  beforeGroup {
    productList = javaClass.classLoader!!.getResourceAsStream("api-response/product.json")
        .let { it.source()
            .buffer()
            .readUtf8()
        }
        .let {
          val type = Types.newParameterizedType(List::class.java, Product::class.java)
          val adapter = Moshi.Builder()
              .add(KotlinJsonAdapterFactory())
              .build()
              .adapter<List<Product>>(type)
          adapter.fromJson(it)!!
        }
  }

  beforeEachGroup {
    every { repository.getProductList(groupBuyId) } returns productList

    viewModel = MainViewModel(groupBuyId, repository)
  }

  Feature("Test MainViewModel") {
    Scenario("load all product") {
      When ("load all product") {
        viewModel.loadProduct()
      }

      Then("product list size should be 7.") {
        val productList = viewModel.productList.value!!
        assert(productList.size == 7)
      }
    }

    Scenario("load all product and filter price") {
      When ("load all product and filter price") {
        viewModel.loadProduct()
        viewModel.filterPriceAbove500()
      }

      Then("product list size should be 4.") {
        val productList = viewModel.productList.value!!
        assert(productList.size == 4)
      }
    }
  }
})