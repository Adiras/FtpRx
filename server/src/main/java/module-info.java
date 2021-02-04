module ftprx.server {
    requires org.tinylog.api;
    requires com.google.guice;
    requires com.google.gson;
    requires jsr305;
    requires owner;
    exports com.ftprx.server;
    exports com.ftprx.server.account;
    opens com.ftprx.server.account to com.google.gson;
    exports com.ftprx.server.channel;
}