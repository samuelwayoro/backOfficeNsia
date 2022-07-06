
package mmwservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mmwservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PinWithdrawalResponse_QNAME = new QName("http://mmwservice/", "pinWithdrawalResponse");
    private final static QName _GetTransactionStatusResponse_QNAME = new QName("http://mmwservice/", "getTransactionStatusResponse");
    private final static QName _GetTransactionStatus_QNAME = new QName("http://mmwservice/", "getTransactionStatus");
    private final static QName _WalletToBankResponse_QNAME = new QName("http://mmwservice/", "WalletToBankResponse");
    private final static QName _GetMobileAccountStatus_QNAME = new QName("http://mmwservice/", "getMobileAccountStatus");
    private final static QName _UnlinkBankAccount_QNAME = new QName("http://mmwservice/", "unlinkBankAccount");
    private final static QName _UnlinkBankAccountResponse_QNAME = new QName("http://mmwservice/", "unlinkBankAccountResponse");
    private final static QName _LinkBankAccountResponse_QNAME = new QName("http://mmwservice/", "linkBankAccountResponse");
    private final static QName _BankToWalletResponse_QNAME = new QName("http://mmwservice/", "BankToWalletResponse");
    private final static QName _LinkBankAccount_QNAME = new QName("http://mmwservice/", "linkBankAccount");
    private final static QName _BankToWallet_QNAME = new QName("http://mmwservice/", "BankToWallet");
    private final static QName _GetMobileAccountStatusResponse_QNAME = new QName("http://mmwservice/", "getMobileAccountStatusResponse");
    private final static QName _PinWithdrawal_QNAME = new QName("http://mmwservice/", "pinWithdrawal");
    private final static QName _WalletToBank_QNAME = new QName("http://mmwservice/", "WalletToBank");
    private final static QName _GetMobileAccountStatusWithCode_QNAME = new QName("http://mmwservice/", "getMobileAccountStatusWithCode");
    private final static QName _GetMobileAccountStatusWithCodeResponse_QNAME = new QName("http://mmwservice/", "getMobileAccountStatusWithCodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mmwservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMobileAccountStatusResponse }
     * 
     */
    public GetMobileAccountStatusResponse createGetMobileAccountStatusResponse() {
        return new GetMobileAccountStatusResponse();
    }

    /**
     * Create an instance of {@link BankToWallet }
     * 
     */
    public BankToWallet createBankToWallet() {
        return new BankToWallet();
    }

    /**
     * Create an instance of {@link BankToWalletResponse }
     * 
     */
    public BankToWalletResponse createBankToWalletResponse() {
        return new BankToWalletResponse();
    }

    /**
     * Create an instance of {@link LinkBankAccount }
     * 
     */
    public LinkBankAccount createLinkBankAccount() {
        return new LinkBankAccount();
    }

    /**
     * Create an instance of {@link GetMobileAccountStatusWithCodeResponse }
     * 
     */
    public GetMobileAccountStatusWithCodeResponse createGetMobileAccountStatusWithCodeResponse() {
        return new GetMobileAccountStatusWithCodeResponse();
    }

    /**
     * Create an instance of {@link GetMobileAccountStatusWithCode }
     * 
     */
    public GetMobileAccountStatusWithCode createGetMobileAccountStatusWithCode() {
        return new GetMobileAccountStatusWithCode();
    }

    /**
     * Create an instance of {@link WalletToBank }
     * 
     */
    public WalletToBank createWalletToBank() {
        return new WalletToBank();
    }

    /**
     * Create an instance of {@link PinWithdrawal }
     * 
     */
    public PinWithdrawal createPinWithdrawal() {
        return new PinWithdrawal();
    }

    /**
     * Create an instance of {@link WalletToBankResponse }
     * 
     */
    public WalletToBankResponse createWalletToBankResponse() {
        return new WalletToBankResponse();
    }

    /**
     * Create an instance of {@link GetMobileAccountStatus }
     * 
     */
    public GetMobileAccountStatus createGetMobileAccountStatus() {
        return new GetMobileAccountStatus();
    }

    /**
     * Create an instance of {@link GetTransactionStatus }
     * 
     */
    public GetTransactionStatus createGetTransactionStatus() {
        return new GetTransactionStatus();
    }

    /**
     * Create an instance of {@link GetTransactionStatusResponse }
     * 
     */
    public GetTransactionStatusResponse createGetTransactionStatusResponse() {
        return new GetTransactionStatusResponse();
    }

    /**
     * Create an instance of {@link PinWithdrawalResponse }
     * 
     */
    public PinWithdrawalResponse createPinWithdrawalResponse() {
        return new PinWithdrawalResponse();
    }

    /**
     * Create an instance of {@link LinkBankAccountResponse }
     * 
     */
    public LinkBankAccountResponse createLinkBankAccountResponse() {
        return new LinkBankAccountResponse();
    }

    /**
     * Create an instance of {@link UnlinkBankAccount }
     * 
     */
    public UnlinkBankAccount createUnlinkBankAccount() {
        return new UnlinkBankAccount();
    }

    /**
     * Create an instance of {@link UnlinkBankAccountResponse }
     * 
     */
    public UnlinkBankAccountResponse createUnlinkBankAccountResponse() {
        return new UnlinkBankAccountResponse();
    }

    /**
     * Create an instance of {@link XmlStruct }
     * 
     */
    public XmlStruct createXmlStruct() {
        return new XmlStruct();
    }

    /**
     * Create an instance of {@link TransRequest }
     * 
     */
    public TransRequest createTransRequest() {
        return new TransRequest();
    }

    /**
     * Create an instance of {@link TransResponse }
     * 
     */
    public TransResponse createTransResponse() {
        return new TransResponse();
    }

    /**
     * Create an instance of {@link LinkRequest }
     * 
     */
    public LinkRequest createLinkRequest() {
        return new LinkRequest();
    }

    /**
     * Create an instance of {@link UnLinkRequest }
     * 
     */
    public UnLinkRequest createUnLinkRequest() {
        return new UnLinkRequest();
    }

    /**
     * Create an instance of {@link BankRequest }
     * 
     */
    public BankRequest createBankRequest() {
        return new BankRequest();
    }

    /**
     * Create an instance of {@link MaiResponse }
     * 
     */
    public MaiResponse createMaiResponse() {
        return new MaiResponse();
    }

    /**
     * Create an instance of {@link LinkResponse }
     * 
     */
    public LinkResponse createLinkResponse() {
        return new LinkResponse();
    }

    /**
     * Create an instance of {@link MaiRequest }
     * 
     */
    public MaiRequest createMaiRequest() {
        return new MaiRequest();
    }

    /**
     * Create an instance of {@link MaiRequestWithCode }
     * 
     */
    public MaiRequestWithCode createMaiRequestWithCode() {
        return new MaiRequestWithCode();
    }

    /**
     * Create an instance of {@link PinWithdrawalRequest }
     * 
     */
    public PinWithdrawalRequest createPinWithdrawalRequest() {
        return new PinWithdrawalRequest();
    }

    /**
     * Create an instance of {@link BankResponse }
     * 
     */
    public BankResponse createBankResponse() {
        return new BankResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinWithdrawalResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "pinWithdrawalResponse")
    public JAXBElement<PinWithdrawalResponse> createPinWithdrawalResponse(PinWithdrawalResponse value) {
        return new JAXBElement<PinWithdrawalResponse>(_PinWithdrawalResponse_QNAME, PinWithdrawalResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getTransactionStatusResponse")
    public JAXBElement<GetTransactionStatusResponse> createGetTransactionStatusResponse(GetTransactionStatusResponse value) {
        return new JAXBElement<GetTransactionStatusResponse>(_GetTransactionStatusResponse_QNAME, GetTransactionStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getTransactionStatus")
    public JAXBElement<GetTransactionStatus> createGetTransactionStatus(GetTransactionStatus value) {
        return new JAXBElement<GetTransactionStatus>(_GetTransactionStatus_QNAME, GetTransactionStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WalletToBankResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "WalletToBankResponse")
    public JAXBElement<WalletToBankResponse> createWalletToBankResponse(WalletToBankResponse value) {
        return new JAXBElement<WalletToBankResponse>(_WalletToBankResponse_QNAME, WalletToBankResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobileAccountStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getMobileAccountStatus")
    public JAXBElement<GetMobileAccountStatus> createGetMobileAccountStatus(GetMobileAccountStatus value) {
        return new JAXBElement<GetMobileAccountStatus>(_GetMobileAccountStatus_QNAME, GetMobileAccountStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlinkBankAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "unlinkBankAccount")
    public JAXBElement<UnlinkBankAccount> createUnlinkBankAccount(UnlinkBankAccount value) {
        return new JAXBElement<UnlinkBankAccount>(_UnlinkBankAccount_QNAME, UnlinkBankAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnlinkBankAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "unlinkBankAccountResponse")
    public JAXBElement<UnlinkBankAccountResponse> createUnlinkBankAccountResponse(UnlinkBankAccountResponse value) {
        return new JAXBElement<UnlinkBankAccountResponse>(_UnlinkBankAccountResponse_QNAME, UnlinkBankAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkBankAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "linkBankAccountResponse")
    public JAXBElement<LinkBankAccountResponse> createLinkBankAccountResponse(LinkBankAccountResponse value) {
        return new JAXBElement<LinkBankAccountResponse>(_LinkBankAccountResponse_QNAME, LinkBankAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankToWalletResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "BankToWalletResponse")
    public JAXBElement<BankToWalletResponse> createBankToWalletResponse(BankToWalletResponse value) {
        return new JAXBElement<BankToWalletResponse>(_BankToWalletResponse_QNAME, BankToWalletResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LinkBankAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "linkBankAccount")
    public JAXBElement<LinkBankAccount> createLinkBankAccount(LinkBankAccount value) {
        return new JAXBElement<LinkBankAccount>(_LinkBankAccount_QNAME, LinkBankAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankToWallet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "BankToWallet")
    public JAXBElement<BankToWallet> createBankToWallet(BankToWallet value) {
        return new JAXBElement<BankToWallet>(_BankToWallet_QNAME, BankToWallet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobileAccountStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getMobileAccountStatusResponse")
    public JAXBElement<GetMobileAccountStatusResponse> createGetMobileAccountStatusResponse(GetMobileAccountStatusResponse value) {
        return new JAXBElement<GetMobileAccountStatusResponse>(_GetMobileAccountStatusResponse_QNAME, GetMobileAccountStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinWithdrawal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "pinWithdrawal")
    public JAXBElement<PinWithdrawal> createPinWithdrawal(PinWithdrawal value) {
        return new JAXBElement<PinWithdrawal>(_PinWithdrawal_QNAME, PinWithdrawal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WalletToBank }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "WalletToBank")
    public JAXBElement<WalletToBank> createWalletToBank(WalletToBank value) {
        return new JAXBElement<WalletToBank>(_WalletToBank_QNAME, WalletToBank.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobileAccountStatusWithCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getMobileAccountStatusWithCode")
    public JAXBElement<GetMobileAccountStatusWithCode> createGetMobileAccountStatusWithCode(GetMobileAccountStatusWithCode value) {
        return new JAXBElement<GetMobileAccountStatusWithCode>(_GetMobileAccountStatusWithCode_QNAME, GetMobileAccountStatusWithCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMobileAccountStatusWithCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mmwservice/", name = "getMobileAccountStatusWithCodeResponse")
    public JAXBElement<GetMobileAccountStatusWithCodeResponse> createGetMobileAccountStatusWithCodeResponse(GetMobileAccountStatusWithCodeResponse value) {
        return new JAXBElement<GetMobileAccountStatusWithCodeResponse>(_GetMobileAccountStatusWithCodeResponse_QNAME, GetMobileAccountStatusWithCodeResponse.class, null, value);
    }

}
