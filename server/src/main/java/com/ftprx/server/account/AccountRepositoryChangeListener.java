package com.ftprx.server.account;

import javax.annotation.Nonnull;

/**
 * Interface that receives notifications of changes to an {@link AccountRepository}.
 */
public interface AccountRepositoryChangeListener {
    void onInsertAccount(@Nonnull Account account);
    void onDeleteEvent(@Nonnull Account account);
    void onUpdateEvent(@Nonnull Account account);
}
