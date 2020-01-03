package aids61517.unittest.viewmodel

import aids61517.unittest.data.Product
import aids61517.unittest.repository.ProductRepository
import androidx.lifecycle.MutableLiveData

class MainViewModel(
  private val groupBuyId: String,
  private val repository: ProductRepository
) {
  val productList by lazy {
    MutableLiveData<List<Product>>()
  }

  private lateinit var _productList: List<Product>

  fun loadProduct() {
    _productList = repository.getProductList(groupBuyId)
    this.productList.value = _productList
  }

  fun filterPriceAbove500() {
    this.productList.value = _productList.filter { it.price > 500 }
  }
}