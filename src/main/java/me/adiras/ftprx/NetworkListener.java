package me.adiras.ftprx;

public interface NetworkListener {
    void onRequestReceive(Connection connection, String request);
    void onConnectionEstablishment(Connection connection);
}
