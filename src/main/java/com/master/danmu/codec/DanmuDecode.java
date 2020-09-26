package com.master.danmu.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class DanmuDecode extends LengthFieldBasedFrameDecoder {

    public DanmuDecode(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }


}
