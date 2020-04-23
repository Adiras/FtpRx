package com.ftprx.server.account;

import javax.annotation.Nullable;
import java.util.Optional;

public interface AccountRepository {
    @Nullable
    Account findAccountByUsername(String username);
}
