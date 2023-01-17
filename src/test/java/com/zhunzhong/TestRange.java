package com.zhunzhong;

import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import com.zhunzhong.demo.pojo.entity.RollingDoorDto;
import org.junit.Test;

import javax.validation.Valid;

/**
 * @author: zhunzhong
 * @date: 2022-09-14 08:47
 * @description: todo
 */
public class TestRange {


    @Test
    public void test(){

        Range<Integer> range1 = Range.closed(0, 9);
        RangeSet<Integer> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.add(Range.closed(1,10));
        //连续区间会被合并   [15,22]
        treeRangeSet.add(Range.closedOpen(15,20));
        treeRangeSet.add(Range.closed(18,22));

        treeRangeSet.asRanges().forEach(x-> System.out.println(x));
        System.out.println(treeRangeSet.asRanges().size());

    }

    void rollingDoor(@Valid RollingDoorDto rollingDoorDTO){

    }

    @Test
    public void testValid(){
        rollingDoor(new RollingDoorDto());
    }
}
