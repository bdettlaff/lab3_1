package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.hamcrest.core.Is.is;

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

    @Test
    public void shouldReturnTrueIfInvoiceRequestReturnsOnePositionWhenRequestsForOnePosition(){
        invoiceRequest.add(requestItem);

        Mockito.when(taxPolicy.calculateTax(any(),any())).thenReturn(tax);
        Invoice invoice = bookKeeper.issuance(invoiceRequest,taxPolicy);

        assertThat(invoice.getItems().size(), is(1));
    }

    @Test
    public void shouldReturnTrueIfInvoiceRequestWithTwoPositionShouldInvokesCalculateTaxMethodTwice(){
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        Mockito.when(taxPolicy.calculateTax(any(),any())).thenReturn(tax);
        bookKeeper.issuance(invoiceRequest,taxPolicy);

        verify(taxPolicy,times(2)).calculateTax(ProductType.FOOD,money);

    }

    @Test
    public void shouldReturnTrueIfInvoiceRequestReturnsThreePositionsWhenRequestsForThreePositions(){
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        Mockito.when(taxPolicy.calculateTax(any(),any())).thenReturn(tax);
        Invoice invoice = bookKeeper.issuance(invoiceRequest,taxPolicy);

        assertThat(invoice.getItems().size(), is(3));
    }
}
