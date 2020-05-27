package com.ftprx.server.command;

import static com.ftprx.server.CommandCode.*;

public class BootstrapCommands {
    public BootstrapCommands(CommandLookupTable e) {
        e.registerCommand(USER, new UsernameCommand());
        e.registerCommand(PASS, new PasswordCommand());
        e.registerCommand(ACCT, new AccountCommand());
        e.registerCommand(PWD, new PrintWorkingDirectoryCommand());
        e.registerCommand(XPWD, new PrintWorkingDirectoryCommand());
        e.registerCommand(PORT, new DataPortCommand());
        e.registerCommand(STOR, new StoreCommand());
        e.registerCommand(NLST, new NameListCommand());
        e.registerCommand(QUIT, new LogoutCommand());
        e.registerCommand(PASV, new PassiveCommand());
        e.registerCommand(CWD, new ChangeWorkingDirectoryCommand());
        e.registerCommand(MKD, new MakeDirectoryCommand());
        e.registerCommand(SYST, new SystemCommand());
        e.registerCommand(TYPE, new RepresentationTypeCommand());
        e.registerCommand(NOOP, new NoopCommand());
        e.registerCommand(LIST, new ListCommand());
    }
}
