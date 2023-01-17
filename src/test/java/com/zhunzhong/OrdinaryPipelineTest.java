package com.zhunzhong;

import com.zhunzhong.demo.pipleline.OrdinaryPipeline;
import com.zhunzhong.demo.pipleline.Pipeline;
import com.zhunzhong.demo.pipleline.PipelineContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class OrdinaryPipelineTest {

    @Test
    public void test() {
        Pipeline<?> pipeline =
                OrdinaryPipeline.getInstance(
                        Arrays.asList(new DemoPipeline("1"), new DemoPipeline("2"), new DemoPipeline("3")));
        Assert.assertEquals("1->2->3", pipeline.toString());

    }

    private static final class DemoPipeline extends OrdinaryPipeline<String> {

        public DemoPipeline(String name) {
            super(name);
        }

        @Override
        public void process(PipelineContext ctx, String s) {
            System.out.println("aa");
            // TODO
        }
    }

}
