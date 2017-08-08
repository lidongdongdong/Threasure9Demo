package com.zhuoxin.com.threasure9demo.map;

import com.zhuoxin.com.threasure9demo.treasure.Treasure;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */

public interface MapFragmentView {
    void showMessage(String message);
    void setTreasureData(List<Treasure> treasureList);
}
