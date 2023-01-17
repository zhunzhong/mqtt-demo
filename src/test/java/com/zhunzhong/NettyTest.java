package com.zhunzhong;

import io.netty.channel.epoll.Epoll;
import org.apache.rocketmq.remoting.common.RemotingUtil;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.junit.Test;

/**
 * @author: zhunzhong
 * @date: 2022-09-13 09:14
 * @description: todo
 */
public class NettyTest {

    NettyServerConfig nettyServerConfig;

    @Test
    public void testNio(){
        boolean useEpoll= RemotingUtil.isLinuxPlatform()
                && nettyServerConfig.isUseEpollNativeSelector()
                && Epoll.isAvailable();
    }
}
