package com.ftprx.server.account;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface for basic operations on a account repository.
 */
public interface AccountRepository {

    /**
     * Insert a given account.
     * @param account must not be {@literal null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void update(@Nonnull Account account);

    Account findByUsername(String username);

    List<Account> findAll();

    /**
     * Insert a given account.
     * @param account must not be {@literal null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void insert(@Nonnull Account account) throws AccountInsertException;

    /**
     * Delete a given account.
     * @param account must not be {@literal null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void delete(@Nonnull Account account);
}
