package com.ftprx.server.process;

import java.io.File;

public class UnixListingFormatter implements ListingFormatter {

    @Override
    public String format(File file) {
        return file.getName();
    }
}
