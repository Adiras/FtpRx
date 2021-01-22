package com.ftprx.server.account;

import javax.annotation.Nullable;

/**
 * A repository that allows listeners to track changes when they occur.
 */
public interface ObservableAccountRepository extends AccountRepository {

    /**
     * Add a listener to this observable list.
     * @param listener the listener for listening to the list changes
     */
    void addListener(@Nullable AccountRepositoryChangeListener listener);

    /**
     * Tries to remove a listener from this observable list. If the listener is not
     * attached to this list, nothing happens.
     * @param listener a listener to remove
     */
    void removeListener(@Nullable AccountRepositoryChangeListener listener);
}
