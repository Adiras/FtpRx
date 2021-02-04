/*
 * Copyright 2019, FtpRx Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftprx.server.account;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Interface for basic operations on a account repository.
 */
public interface AccountRepository {

    /**
     * Insert a given account.
     * @param account must not be {@code null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void update(@Nonnull Account account);

    /**
     * Retrieves an account by its username.
     * @param username must not be {@code null}
     */
    Account findByUsername(@Nonnull String username);

    /**
     * Returns all existing account instances.
     * If the repository is empty, it will return an empty list.
     */
    List<Account> findAll();

    /**
     * Insert a given account.
     * @param account must not be {@code null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void insert(@Nonnull Account account) throws AccountInsertException;

    /**
     * Delete a given account.
     * @param account must not be {@code null}.
     * @throws NullPointerException if {@code account} is {@code null}
     */
    void delete(@Nonnull Account account);
}
