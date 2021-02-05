package com.ftprx.server.command

import com.ftprx.server.CommandLookupTable
import com.ftprx.server.channel.Client
import com.ftprx.server.channel.Command
import spock.lang.Specification

class NoopCommandSpec extends Specification {
    def "test noop command handler"() {
        given:
        def client = new Client(Mock(Socket.class))
        expect:
        1 * client.sendReply(200, _)
    }
}
