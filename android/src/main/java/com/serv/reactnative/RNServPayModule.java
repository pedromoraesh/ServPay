
package com.serv.reactnative;

import br.com.uol.pslibs.checkout_in_app.PSCheckout;
import br.com.uol.pslibs.checkout_in_app.transparent.listener.PSBilletListener;
import br.com.uol.pslibs.checkout_in_app.transparent.listener.PSCheckoutListener;
import br.com.uol.pslibs.checkout_in_app.transparent.listener.PSInstallmentsListener;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.InstallmentVO;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.PSBilletRequest;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.PSCheckoutResponse;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.PSInstallmentsResponse;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.PSTransparentDefaultRequest;
import br.com.uol.pslibs.checkout_in_app.transparent.vo.PaymentResponseVO;
import br.com.uol.pslibs.checkout_in_app.wallet.listener.MainCardCallback;
import br.com.uol.pslibs.checkout_in_app.wallet.util.PSCheckoutConfig;
import br.com.uol.pslibs.checkout_in_app.wallet.view.components.PaymentButton;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PSCheckoutRequest;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PSWalletMainCardVO;
import br.com.uol.pslibs.checkout_in_app.wallet.vo.PagSeguroResponse;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNServPayModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static String SELLER_EMAIL = "Email do vendedor";
  private static String SELLER_TOKEN = "Token do vendedor";
  private PSCheckoutConfig psCheckoutConfig = new PSCheckoutConfig();


  public RNServPayModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNServPay";
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    PSCheckout.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
  }

  @ReactMethod
  private void configureSeller(String seller_email, String seller_token){
    this.SELLER_EMAIL = seller_email;
    this.SELLER_TOKEN = seller_token;
  }

  @ReactMethod
  private void initTransparent(){
        psCheckoutConfig.setSellerEmail(SELLER_EMAIL);
        psCheckoutConfig.setSellerToken(SELLER_TOKEN);

        //Inicializa apenas os recursos de pagamento transparente e boleto
        PSCheckout.initTransparent(getCurrentActivity(), psCheckoutConfig);
  }

  @ReactMethod
  public void payCreditCard(String cpf, String name, String email, String ddd, String phone, String street, 
                    String complement, String addressNumber, String bairro, String city, String state, 
                    String country, String cep, String total, String valorUnitario, String description, 
                    int quantity, String credicardNumber, String cvv, String expMonth, String expYear, 
                    String birthDate){
      
      // Objeto utilizado para parcelamento. Deve-se passar como argumento no .setInstallments()
      //InstallmentVO installment = new InstallmentVO(); - Instância o objeto InstallmentVO
      //installment.setAmount(Double.parseDouble(total)); - Valor a ser parcelado
      //installment.setQuantity(1); - Quantidade de parcelas

      private PSCheckoutListener psCheckoutListener = new PSCheckoutListener() {
      @Override
      public void onSuccess(PSCheckoutResponse responseVO) {
        // responseVO.getCode() - Código da transação 
        // responseVO.getStatus() - Status da transação
        // responseVO.getMessage() - Mensagem de retorno da transação(Sucesso/falha)
        Toast.makeText(getActivity(), "Sucesso: "+responseVO.getMessage(), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(PSCheckoutResponse responseVO) {
        Toast.makeText(getActivity(), "Falhou: "+responseVO.getMessage(), Toast.LENGTH_LONG).show();
      }

      @Override
      public void onProcessing() {
        Toast.makeText(getActivity(), "Processando...", Toast.LENGTH_LONG).show();
      }
    };

    PSTransparentDefaultRequest psTransparentDefaultRequest = new PSTransparentDefaultRequest();
    psTransparentDefaultRequest
        .setDocumentNumber(cpf)
        .setName(name)
        .setEmail(email)
        .setAreaCode(ddd)
        .setPhoneNumber(phone)
        .setStreet(street)
        .setAddressComplement(complement)
        .setAddressNumber(adressNumber)
        .setDistrict(bairro)
        .setCity(city)
        .setState(state)
        .setCountry(country)
        .setPostalCode(cep)
        .setTotalValue(total)
        .setAmount(valorUnitario)
        .setDescriptionPayment(description)
        .setQuantity(quantity)
        .setCreditCard(credicardNumber)
        .setCvv(cvv)
        .setExpMonth(expMonth)
        .setExpYear(expYear)
        .setBirthDate(birthDate)
        .setInstallments(null);
    PSCheckout.payTransparentDefault(psTransparentDefaultRequest, psCheckoutListener, (AppCompatActivity) getCurrentActivity());
  }

  @ReactMethod 
  public void payBillet(String cpf, String name, String email, String ddd, String phone, String street, 
                        String complement, String adressNumber, String bairro, String city, String state, 
                        String country, String cep, Double totalValue, Double valorUnitario, 
                        String description, int quantity){

    private PSBilletListener psBilletListener = new PSBilletListener() {
      @Override
      public void onSuccess(PaymentResponseVO responseVO) {
          // responseVO.getBookletNumber() - número do código de barras do boleto
          // responseVO.getPaymentLink() - link para download do boleto
      }

      @Override
      public void onFailure(Exception e) {
          // Error
      }

      @Override
      public void onProcessing() {
          // Progress
      }
    };
    
    PSBilletRequest psBilletRequest = new PSBilletRequest();
        psBilletRequest
                .setDocumentNumber(cpf)
                .setName(name)
                .setEmail(email)
                .setAreaCode(ddd)
                .setPhoneNumber(phone)
                .setStreet(street)
                .setAddressComplement(complement)
                .setAddressNumber(adressNumber)
                .setDistrict(bairro)
                .setCity(city)
                .setState(state)
                .setCountry(country)
                .setPostalCode(cep)
                .setTotalValue(totalValue)
                .setAmount(valorUnitario)
                .setDescriptionPayment(description)
                .setQuantity(quantity);
        PSCheckout.generateBooklet(psBilletRequest, psBilletListener, (AppCompatActivity) getCurrentActivity());
  }
  
  
}