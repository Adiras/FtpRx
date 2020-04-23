package com.ftprx.server.command;

import static com.ftprx.server.CommandCode.*;

public class BootstrapCommands {
    public BootstrapCommands(CommandDispatcher cd) {
        cd.registerCommand(USER, new UsernameCommand());
        cd.registerCommand(PASS, new PasswordCommand());
        cd.registerCommand(ACCT, new AccountCommand());
        cd.registerCommand(PWD, new PrintWorkingDirectoryCommand());
        cd.registerCommand(XPWD, new PrintWorkingDirectoryCommand());
        cd.registerCommand(PORT, new DataPortCommand());
        cd.registerCommand(STOR, new StoreCommand());
        cd.registerCommand(NLST, new NameListCommand());
        cd.registerCommand(QUIT, new LogoutCommand());
        cd.registerCommand(PASV, new PassiveCommand());
        cd.registerCommand(CWD, new ChangeWorkingDirectoryCommand());
        cd.registerCommand(MKD, new MakeDirectoryCommand());
        cd.registerCommand(SYST, new SystemCommand());
        cd.registerCommand(TYPE, new RepresentationTypeCommand());
        cd.registerCommand(NOOP, new NoopCommand());
        cd.registerCommand(LIST, new ListCommand());
    }
}
