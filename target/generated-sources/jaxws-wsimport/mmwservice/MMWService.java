
package mmwservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebService(name = "MMWService", targetNamespace = "http://mmwservice/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface MMWService {


    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.BankResponse
     */
    @WebMethod(operationName = "WalletToBank")
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "WalletToBank", targetNamespace = "http://mmwservice/", className = "mmwservice.WalletToBank")
    @ResponseWrapper(localName = "WalletToBankResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.WalletToBankResponse")
    public BankResponse walletToBank(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "Request", targetNamespace = "")
        BankRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.LinkResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "unlinkBankAccount", targetNamespace = "http://mmwservice/", className = "mmwservice.UnlinkBankAccount")
    @ResponseWrapper(localName = "unlinkBankAccountResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.UnlinkBankAccountResponse")
    public LinkResponse unlinkBankAccount(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "Request", targetNamespace = "")
        UnLinkRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.MaiResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "getMobileAccountStatusWithCode", targetNamespace = "http://mmwservice/", className = "mmwservice.GetMobileAccountStatusWithCode")
    @ResponseWrapper(localName = "getMobileAccountStatusWithCodeResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.GetMobileAccountStatusWithCodeResponse")
    public MaiResponse getMobileAccountStatusWithCode(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "request", targetNamespace = "")
        MaiRequestWithCode request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.BankResponse
     */
    @WebMethod(operationName = "BankToWallet")
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "BankToWallet", targetNamespace = "http://mmwservice/", className = "mmwservice.BankToWallet")
    @ResponseWrapper(localName = "BankToWalletResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.BankToWalletResponse")
    public BankResponse bankToWallet(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "request", targetNamespace = "")
        BankRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.LinkResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "linkBankAccount", targetNamespace = "http://mmwservice/", className = "mmwservice.LinkBankAccount")
    @ResponseWrapper(localName = "linkBankAccountResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.LinkBankAccountResponse")
    public LinkResponse linkBankAccount(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "Request", targetNamespace = "")
        LinkRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.TransResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "getTransactionStatus", targetNamespace = "http://mmwservice/", className = "mmwservice.GetTransactionStatus")
    @ResponseWrapper(localName = "getTransactionStatusResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.GetTransactionStatusResponse")
    public TransResponse getTransactionStatus(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "request", targetNamespace = "")
        TransRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.MaiResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "getMobileAccountStatus", targetNamespace = "http://mmwservice/", className = "mmwservice.GetMobileAccountStatus")
    @ResponseWrapper(localName = "getMobileAccountStatusResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.GetMobileAccountStatusResponse")
    public MaiResponse getMobileAccountStatus(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "request", targetNamespace = "")
        MaiRequest request);

    /**
     * 
     * @param request
     * @param token
     * @return
     *     returns mmwservice.TransResponse
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "")
    @RequestWrapper(localName = "pinWithdrawal", targetNamespace = "http://mmwservice/", className = "mmwservice.PinWithdrawal")
    @ResponseWrapper(localName = "pinWithdrawalResponse", targetNamespace = "http://mmwservice/", className = "mmwservice.PinWithdrawalResponse")
    public TransResponse pinWithdrawal(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "Request", targetNamespace = "")
        PinWithdrawalRequest request);

}
