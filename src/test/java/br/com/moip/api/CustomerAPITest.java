package br.com.moip.api;

import br.com.moip.request.ApiDateRequest;
import br.com.moip.request.CreditCardRequest;
import br.com.moip.request.CustomerRequest;
import br.com.moip.request.FundingInstrumentRequest;
import br.com.moip.request.HolderRequest;
import br.com.moip.request.PhoneRequest;
import br.com.moip.request.ShippingAddressRequest;
import br.com.moip.request.TaxDocumentRequest;
import br.com.moip.resource.Customer;
import com.rodrigosaito.mockwebserver.player.Play;
import com.rodrigosaito.mockwebserver.player.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomerAPITest {

    @Rule
    public Player player = new Player();
    private CustomerAPI api;

    @Before
    public void setUp() {
        api = new CustomerAPI(new ClientFactory().client(player.getURL("").toString()));
    }

    @Play("customer/create")
    @Test
    public void createCustomer() {
        CustomerRequest customerRequest = new CustomerRequest()
                .ownId("own_id")
                .email("jose_silva0@email.com")
                .birthdate(new ApiDateRequest()
                        .date(new GregorianCalendar(1988, Calendar.DECEMBER, 30)
                                .getTime()))
                .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                .phone(new PhoneRequest()
                        .setNumber("66778899")
                        .setAreaCode("11"))
                .shippingAddressRequest(new ShippingAddressRequest()
                        .city("Sao Paulo")
                        .complement("8")
                        .district("Itaim")
                        .state("SP")
                        .streetNumber("2927")
                        .zipCode("01234000")
                        .country("BRA"));

        Customer customer = api.create(customerRequest);

        assertEquals("CUS-Y6L4AGQN8HKQ", customer.getId());
        assertEquals("own_id", customer.getOwnId());
        assertEquals("Jose Silva", customer.getFullname());
        assertEquals("11", customer.getPhone().getAreaCode());
        assertEquals("66778899", customer.getPhone().getNumber());
        assertEquals("22222222222", customer.getTaxDocument().getNumber());
        assertEquals("Sao Paulo", customer.getShippingAddress().getCity());
        assertEquals("8", customer.getShippingAddress().getComplement());
        assertEquals("Itaim", customer.getShippingAddress().getDistrict());
        assertEquals("SP", customer.getShippingAddress().getState());
        assertEquals("01234000", customer.getShippingAddress().getZipCode());
        assertEquals("BRA", customer.getShippingAddress().getCountry());
    }

    @Play("customer/get")
    @Test
    public void getCustomer() {
        Customer customer = api.get("CUS-Y6L4AGQN8HKQ");

        assertEquals("CUS-Y6L4AGQN8HKQ", customer.getId());
        assertEquals("own_id", customer.getOwnId());
        assertEquals("Jose Silva", customer.getFullname());
        assertEquals("11", customer.getPhone().getAreaCode());
        assertEquals("66778899", customer.getPhone().getNumber());
        assertEquals("22222222222", customer.getTaxDocument().getNumber());
        assertEquals("Sao Paulo", customer.getShippingAddress().getCity());
        assertEquals("8", customer.getShippingAddress().getComplement());
        assertEquals("Itaim", customer.getShippingAddress().getDistrict());
        assertEquals("SP", customer.getShippingAddress().getState());
        assertEquals("01234000", customer.getShippingAddress().getZipCode());
        assertEquals("BRA", customer.getShippingAddress().getCountry());
    }

    @Play("customer/create_minimal_customer")
    @Test
    public void createMinimalCustomer() {
        CustomerRequest customerRequest = new CustomerRequest()
                .ownId("own_id")
                .email("jose_silva0@email.com")
                .birthdate(new ApiDateRequest()
                        .date(new GregorianCalendar(1988, Calendar.DECEMBER, 30)
                                .getTime()));

        Customer customer = api.create(customerRequest);

        assertEquals("CUS-YZA5DJ41GFXU", customer.getId());
        assertEquals("1486483588", customer.getOwnId());
        assertEquals("Jose Silva", customer.getFullname());
        assertNull(customer.getPhone());
        assertNull(customer.getTaxDocument());
        assertNull(customer.getShippingAddress());
    }

    @Play("customer/create_customer_with_funding_instrument")
    @Test
    public void createCustomerWithFundingInstrument() {
        CustomerRequest customerRequest = new CustomerRequest()
                .ownId("own_id")
                .email("jose_silva0@email.com")
                .birthdate(new ApiDateRequest()
                        .date(new GregorianCalendar(1988, Calendar.DECEMBER, 30)
                                .getTime()))
                .taxDocument(TaxDocumentRequest.cpf("22222222222"))
                .phone(new PhoneRequest()
                        .setNumber("66778899")
                        .setAreaCode("11"))
                .shippingAddressRequest(new ShippingAddressRequest()
                        .city("Sao Paulo")
                        .complement("8")
                        .district("Itaim")
                        .state("SP")
                        .streetNumber("2927")
                        .zipCode("01234000")
                        .country("BRA"))
                .fundingInstrument(new FundingInstrumentRequest()
                        .creditCard(new CreditCardRequest()
                                .holder(new HolderRequest()
                                        .birthdate("1988-12-30")
                                        .fullname("Jose Portador")
                                        .phone(new PhoneRequest()
                                                        .setAreaCode("11")
                                                        .setNumber("11111111")
                                                        .countryCode("55")
                                        ))));

        Customer customer = api.create(customerRequest);

        assertEquals("CUS-O7FVGTW6DBJI", customer.getId());
        assertEquals("own_id", customer.getOwnId());
        assertEquals("Jose Silva", customer.getFullname());
        assertEquals("11", customer.getPhone().getAreaCode());
        assertEquals("66778899", customer.getPhone().getNumber());
        assertEquals("22222222222", customer.getTaxDocument().getNumber());
        assertEquals("Sao Paulo", customer.getShippingAddress().getCity());
        assertEquals("8", customer.getShippingAddress().getComplement());
        assertEquals("Itaim", customer.getShippingAddress().getDistrict());
        assertEquals("SP", customer.getShippingAddress().getState());
        assertEquals("01234000", customer.getShippingAddress().getZipCode());
        assertEquals("BRA", customer.getShippingAddress().getCountry());
        assertEquals("MASTERCARD", customer.getFundingInstrument().getCreditCard().getBrand());
        assertEquals("555566", customer.getFundingInstrument().getCreditCard().getFirst6());
        assertEquals("1111", customer.getFundingInstrument().getCreditCard().getLast4());
    }

}
