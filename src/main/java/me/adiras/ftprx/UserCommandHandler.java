package me.adiras.ftprx;

public class UserCommandHandler implements CommandHandler {
    @Override
    public void process(ServerContext context, Connection sender, String arguments) {
        System.out.println("USER");
        sender.sendResponse(Response.builder()
                .code("100")
                .argument("Test")
                .build());
    }
}
