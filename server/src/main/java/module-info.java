module ftprx.server {
    requires org.tinylog.api;
    requires com.google.guice;
    requires com.google.gson;
    requires owner;
    requires org.jetbrains.annotations;
    exports com.ftprx.server;
    exports com.ftprx.server.account;
    opens com.ftprx.server.account to com.google.gson;
    exports com.ftprx.server.channel;
}