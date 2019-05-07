package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

public class BookKeeperTest {

    private BookKeeper bookKeeper;
    private InvoiceRequest invoiceRequest;
    private TaxPolicy taxPolicy;

    private RequestItem requestItem;
    private Money money;
    private ClientData clientData;
    private Tax tax;
    private ProductData productData;

    @Before
    public void setUp(){
        bookKeeper = new BookKeeper(new InvoiceFactory());
        clientData = new ClientData(Id.generate(),"stubOfClientData");
        invoiceRequest = new InvoiceRequest(clientData);
        taxPolicy = Mockito.mock(TaxPolicy.class);
        money = Money.ZERO;
        productData = new ProductData(Id.generate(), money, "stubOfProduct", ProductType.FOOD,new Date());
        requestItem = new RequestItem(productData,0, money);
        tax = new Tax(money, "taxStub");
    }

}
