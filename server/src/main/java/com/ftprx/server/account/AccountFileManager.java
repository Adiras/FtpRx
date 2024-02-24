package com.ftprx.server.account;

import com.ftprx.server.account.Account;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * An interface for reading from and writing to a file containing account list.
 */
public interface AccountFileManager {
    void write(Writer writer, List<Account> accounts);
    List<Account> read(Reader reader);
}
