package com.ftprx.server;

import com.ftprx.server.account.Account;
import com.ftprx.server.repository.FileAccountRepository;

public class Test {
    public static void main(String[] args) {
        FileAccountRepository fileAccountRepository = new FileAccountRepository("C:\\Users\\wkacp\\Desktop\\ftprx\\server\\src\\main\\resources\\accounts.json");
        fileAccountRepository.insert(new Account("sdff", "dsf", "asd"));
//        System.out.println(fileAccountRepository.findAll());
//        fileAccountRepository.delete("sdff");

    }
}
