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
