package com.ftprx.server.command

import spock.lang.Specification

class NoopCommandTest extends Specification {
    def "test noop command handler"() {
        given: "a command handler"
        def handler = NoopCommand

        and: "an command"
        def command = Command("NOOP")

        and: "a mock client"
        def client = Client(Mock(Socket.class))

        when: "we execute given command"
        handler.execute(c)

        then:
        1 * client.sendReply(200, _)
    }
}
