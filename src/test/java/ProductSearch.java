
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;

public class ProductSearch extends BaseTest {

    String productName = "ber";// "Walnuts";
    String notExistingProduct = "xyz";
    String productInCard = "Raspberry - 1/4 Kg";//Walnuts - 1/4 Kg";
    String deliverCountry = "United States";
    String targetProduct = "Carrot - 1 Kg";
    int desiredNumber = 2;

    @Test(groups = {"regression","search"})
    public void searchProductByLetters() {

        CatalogPage catalogPage = new CatalogPage(getDriver());
        List<String> berProducts = catalogPage.searchProductInCatalog(productName);
        Assert.assertFalse(berProducts.isEmpty());
        System.out.println(berProducts);
        for (String item : berProducts) {
            Assert.assertTrue(item.toLowerCase().contains(productName.toLowerCase()));
        }


    }


    @Test(groups = {"regression","smoke","search","e2e"})
    public void placeOrderE2E() {

        CatalogPage catalogPage = new CatalogPage(getDriver());
        List<String> foundProduct = catalogPage.searchProductInCatalog(productName);
        Assert.assertTrue(foundProduct.contains(productInCard), "product not found " + productInCard);
        for (String item : foundProduct) {
            Assert.assertTrue(item.toLowerCase().contains(productName.toLowerCase()
            ), "product not found " + item);
        }
        // go from product name to product card
        catalogPage.incrementQuantity(productInCard);
        int price = catalogPage.getProductPrice(productInCard);
        int quantity = catalogPage.getProductQuantity(productInCard);
        int expectedPrice = price * quantity;

        Assert.assertEquals(quantity, desiredNumber);

        catalogPage.addToCart(productInCard);
        //wait until element will reload and price occur
        catalogPage.waitForHeaderPrice(expectedPrice);
        int cartPrice = catalogPage.getHeaderPrice();
        Assert.assertEquals(cartPrice, expectedPrice);
        int itemsNumber = catalogPage.getItemsNumberInHeader();
        Assert.assertEquals(itemsNumber, 1);
        //go to cartPreview
        CartPreview cartPreview = catalogPage.cartPreview();
        String itemNameInCheckingTab = cartPreview.productNameInPreview();
        String itemQuantityCheckingTab = cartPreview.productQuantityInPreview();
        Assert.assertTrue(itemNameInCheckingTab.contains(productName));
        Assert.assertTrue(itemQuantityCheckingTab.contains(String.valueOf(quantity)));

        //go to cart page
        CartPage cartPage = cartPreview.goToCart();
        cartPage.waitFoUrl("/cart");
        String productNameInOrder = cartPage.getProductNameInOrder(productInCard);

        Assert.assertEquals(productNameInOrder, productInCard);
        //go to check out on country page
        CountryPage countryPage = cartPage.placeOrder();
        countryPage.waitFoUrl("/country");
        countryPage.selectCountry(deliverCountry);
        countryPage.checkLicenceAgreement();
        countryPage.confirmTheOrder();
        String success = countryPage.getSuccessText();
        Assert.assertTrue(success
                .contains("Thank you, your order has been placed successfully"));
    }

    @Test(groups = {"regression","search","negative"})
    public void searchNotExistingProduct() {
        CatalogPage catalogPage = new CatalogPage(getDriver());
        List<String> foundProducts = catalogPage.searchProductInCatalog(notExistingProduct);
        String message = catalogPage.getNoResultMessage();
        Assert.assertEquals(foundProducts.size(), 0);
        Assert.assertEquals(message, "Sorry, no products matched your search!");
        catalogPage.clearSearch();
        List<String> productsAfterClear = catalogPage.getDisplayedProductNames();
        Assert.assertFalse(productsAfterClear.isEmpty());

    }

    @Test(groups = {"regression","quantity"})
    public void updatesSelectedProductQuantityAfterSearch() {
        CatalogPage catalogPage = new CatalogPage(getDriver());
        catalogPage.enterSearchQuery("ro");
        catalogPage.incrementQuantity(targetProduct);
        catalogPage.incrementQuantity(targetProduct);
        int quantity = catalogPage.getProductQuantity(targetProduct);
        Assert.assertEquals(quantity,3);
        System.out.println("Quantity "+quantity);
        catalogPage.setProductQuantity(targetProduct,"1");
        int finalQuantity=catalogPage.getProductQuantity(targetProduct);
        Assert.assertEquals(finalQuantity,Integer.parseInt("1"));
        Assert.fail("Temp failed test");
    }
@Test(enabled = false,dataProvider = "invalidQuantities",groups ={"negative","knownBugs","regression","quantity"})
    public void rejectInvalidProductQuantities(String value){
    CatalogPage catalogPage = new CatalogPage(getDriver());
    catalogPage.enterSearchQuery("ro");
    catalogPage.setProductQuantity(targetProduct,value);
    catalogPage.addToCart(targetProduct);
    CartPreview cartPreview = catalogPage.cartPreview();
    List<String>names=cartPreview.getProductsNames();
    int items =catalogPage.getItemsNumberInHeader();
    int price=catalogPage.getHeaderPrice();
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(names.isEmpty(),"cart contains product for quantity ="+ value);
    softAssert.assertEquals(items,0,"Items quantity = "+value);
    softAssert.assertEquals(price,0,"Price for quantity ="+ value);
    softAssert.assertAll();

}
@DataProvider(name="invalidQuantities")
    public Object[][] invalidQuantities(){
        return new Object[][]{{"-1"},{"-1.5"},{"0"}};
}
}