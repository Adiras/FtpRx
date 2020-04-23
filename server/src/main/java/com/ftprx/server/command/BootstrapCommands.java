package com.ftprx.server.command;

import static com.ftprx.server.util.CommandConstants.*;

public class BootstrapCommands {
    public BootstrapCommands(CommandDispatcher dispatcher) {
        dispatcher.registerCommand(USER, new UsernameCommand());
        dispatcher.registerCommand(PASS, new PasswordCommand());
        dispatcher.registerCommand(ACCT, new AccountCommand());
        dispatcher.registerCommand(PWD, new PrintWorkingDirectoryCommand());
        dispatcher.registerCommand(XPWD, new PrintWorkingDirectoryCommand());
        dispatcher.registerCommand(PORT, new DataPortCommand());
        dispatcher.registerCommand(STOR, new StoreCommand());
        dispatcher.registerCommand(NLST, new NameListCommand());
        dispatcher.registerCommand(QUIT, new LogoutCommand());
        dispatcher.registerCommand(PASV, new PassiveCommand());
        dispatcher.registerCommand(CWD, new ChangeWorkingDirectoryCommand());
        dispatcher.registerCommand(MKD, new MakeDirectoryCommand());
        dispatcher.registerCommand(SYST, new SystemCommand());
        dispatcher.registerCommand(TYPE, new RepresentationTypeCommand());
    }
}
