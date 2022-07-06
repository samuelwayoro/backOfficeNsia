/**
 *
 */
package at.telekom.util.axis;

import java.io.File;
import java.util.Hashtable;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisProperties;
import org.apache.axis.ConfigurationException;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.Handler;
import org.apache.axis.SimpleChain;
import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.components.net.SecureSocketFactory;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.encoding.TypeMappingRegistry;
import org.apache.axis.transport.http.HTTPSender;
import org.apache.axis.transport.http.HTTPTransport;

/**
 * <p>
 * Axis Client Configuration
 * </p>
 *
 * @author Richard Unger
 */
public class SSLClientAxisEngineConfig extends SimpleProvider {

    /**
     * Keystore filename
     */
    private String keystore = null;
    /**
     * Keystore type
     */
    private String keystoretype = null;
    /**
     * Keystore password
     */
    private String keystorepin = null;
    /**
     * Truststore filename
     */
    private String truststore = null;
    /**
     * Truststore PIN
     */
    private String truststorepin = null;
    /**
     * Truststore Type
     */
    private String truststoreType = null;
    /**
     * Log Handler object
     */
    private AxisDebugLogHandler logHandler = null;
    /**
     * Base dir for storing output files
     */
    private File debugBaseDir = null;
    /**
     * true to disable XML formatting
     */
    private boolean disablePrettyXML = false;
    /**
     * true to enable namespace prefix optimization (see Axis docs)
     */
    private boolean enableNamespacePrefixOptimization = false;

    /**
     * Constructor
     */
    public SSLClientAxisEngineConfig() {
        super();
    }

    /**
     * @param arg0
     */
    public SSLClientAxisEngineConfig(EngineConfiguration arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public SSLClientAxisEngineConfig(TypeMappingRegistry arg0) {
        super(arg0);
    }

    /**
     * @see
     * org.apache.axis.configuration.SimpleProvider#configureEngine(org.apache.axis.AxisEngine)
     */
    @Override
    public void configureEngine(AxisEngine engine) throws ConfigurationException {
        super.configureEngine(engine);
        engine.refreshGlobalOptions();
    }

    /**
     * @param keystore the keystore to set
     */
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    /**
     * @param keystorepin the keystorepin to set
     */
    public void setKeystorePassword(String keystorepin) {
        this.keystorepin = keystorepin;
    }

    /**
     * @param keystoretype the keystoretype to set
     */
    public void setKeystoreType(String keystoretype) {
        this.keystoretype = keystoretype;
    }

    /**
     * @param truststore the truststore to set
     */
    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    /**
     * @param truststorepin the truststorepin to set
     */
    public void setTruststorePassword(String truststorepin) {
        this.truststorepin = truststorepin;
    }

    /**
     * @return the truststoreType
     */
    public String getTruststoreType() {
        return truststoreType;
    }

    /**
     * @param truststoreType the truststoreType to set
     */
    public void setTruststoreType(String truststoreType) {
        this.truststoreType = truststoreType;
    }

    /**
     * <p>
     * Initialize, with logging off
     * </p>
     */
    public void initialize() {
        initialize(false);
    }

    /**
     * <p>
     * Initialize
     * </p>
     *
     * @param logging true if logging is desired
     */
    @SuppressWarnings("unchecked")
    public void initialize(boolean logging) {
        final String secureSocketFactoryClassName = "at.telekom.util.axis.CustomSecureSocketFactory";
        AxisProperties.setClassDefault(SecureSocketFactory.class, secureSocketFactoryClassName);
        AxisProperties.setClassOverrideProperty(SecureSocketFactory.class, "axis.socketSecureFactory");
        AxisProperties.setProperty("org.apache.axis.components.net.SecureSocketFactory", secureSocketFactoryClassName);

        AxisProperties.setProperty("axis.socketSecureFactory", secureSocketFactoryClassName);
        AxisProperties.setProperty("axis.socketFactory", "org.apache.axis.components.net.DefaultSocketFactory");

        Hashtable opts = new Hashtable();
        opts.put(AxisEngine.PROP_DISABLE_PRETTY_XML, disablePrettyXML);
        opts.put(AxisEngine.PROP_ENABLE_NAMESPACE_PREFIX_OPTIMIZATION, enableNamespacePrefixOptimization);
        opts.put(AxisEngine.PROP_DEFAULT_CONFIG_CLASS, this.getClass().getName());
        setGlobalOptions(opts);
        Handler pivot = (Handler) new HTTPSender();
        if (keystore != null) {
            pivot.setOption("clientauth", "true");
            pivot.setOption("keystore", keystore);
            if (keystoretype != null) {
                pivot.setOption("keystoreType", keystoretype);
            }
            if (keystorepin != null) {
                pivot.setOption("keypass", keystorepin);
                pivot.setOption("keystorePass", keystorepin);
            }
        }
        if (truststore != null) {
            pivot.setOption("truststore", truststore);
            if (truststoreType != null) {
                pivot.setOption("truststoreType", truststoreType);
            }
            if (truststorepin != null) {
                pivot.setOption("truststorePass", truststorepin);
            }
        }
        if (logging && debugBaseDir != null) {
            pivot.setOption("httplogdirectory", debugBaseDir.getAbsolutePath());
        }
        Handler transport = null;
        // Logging, if desired
        if (logging) {
            SimpleChain reqHandler = new SimpleChain();
            SimpleChain respHandler = new SimpleChain();
            logHandler = new AxisDebugLogHandler();
            if (debugBaseDir != null) {
                logHandler.setBaseDir(debugBaseDir);
            }
            reqHandler.addHandler(logHandler);
            respHandler.addHandler(logHandler);
            transport = new SimpleTargetedChain(reqHandler, pivot, respHandler);
        } else {
            transport = new SimpleTargetedChain(pivot);
        }
        deployTransport(HTTPTransport.DEFAULT_TRANSPORT_NAME, transport);
    }

    /**
     * <p>
     * Get the log handler
     * </p>
     *
     * @return the log handler, or null if no logging
     */
    public AxisDebugLogHandler getLogHandler() {
        return logHandler;
    }

    /**
     * @return the debugBaseDir
     */
    public File getDebugBaseDir() {
        return debugBaseDir;
    }

    /**
     * @param debugBaseDir the debugBaseDir to set
     */
    public void setDebugBaseDir(File debugBaseDir) {
        this.debugBaseDir = debugBaseDir;
    }

    /**
     * @return the disablePrettyXML
     */
    public boolean isDisablePrettyXML() {
        return disablePrettyXML;
    }

    /**
     * @param disablePrettyXML the disablePrettyXML to set
     */
    public void setDisablePrettyXML(boolean disablePrettyXML) {
        this.disablePrettyXML = disablePrettyXML;
    }

    /**
     * @return the enableNamespacePrefixOptimization
     */
    public boolean isEnableNamespacePrefixOptimization() {
        return enableNamespacePrefixOptimization;
    }

    /**
     * @param enableNamespacePrefixOptimization the
     * enableNamespacePrefixOptimization to set
     */
    public void setEnableNamespacePrefixOptimization(
            boolean enableNamespacePrefixOptimization) {
        this.enableNamespacePrefixOptimization = enableNamespacePrefixOptimization;
    }

    /*
	 * HTTP Simple Authentication Example  

  String authorization = Base64Coder.encode(username + ":" + password);
  hd.addHeader("Authorization", "Basic " + authorization);


  Service service = new ServiceLocator();
  Impl impl = service.getImpl();
  javax.xml.rpc.Stub stub = (javax.xml.rpc.Stub) impl;
  stub._setProperty(Stub.USERNAME_PROPERTY, "user");
  stub._setProperty(Stub.PASSWORD_PROPERTY, "pwd");
  
     */
    public String getKeystore() {
        return keystore;
    }

    public String getTruststore() {
        return truststore;
    }

    public String getKeystorepin() {
        return keystorepin;
    }

}
