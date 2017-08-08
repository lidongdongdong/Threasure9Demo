package com.zhuoxin.com.threasure9demo.map;

import com.zhuoxin.com.threasure9demo.net.NetClient;
import com.zhuoxin.com.threasure9demo.treasure.Area;
import com.zhuoxin.com.threasure9demo.treasure.Treasure;
import com.zhuoxin.com.threasure9demo.treasure.TreasureRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/7.
 */

public class MapFragmentPresenter {
    private MapFragmentView mMapFragmentView;
    private Area mArea;

    public MapFragmentPresenter(MapFragmentView mapFragmentView) {
        mMapFragmentView = mapFragmentView;
    }

    public void getTreasureInArea(Area area){
        if (TreasureRepo.getInstance().isCached(area)){
            return;
        }
        mArea = area;
        NetClient.getInstance().getNetRequest().getTreasure(area).enqueue(new Callback<List<Treasure>>() {
            @Override
            public void onResponse(Call<List<Treasure>> call, Response<List<Treasure>> response) {
                if (response.isSuccessful()){
                    List<Treasure> treasureList = response.body();

                    if (treasureList==null){
                        mMapFragmentView.showMessage("未知错误");
                        return;
                    }

                    //缓存Area,缓存宝藏
                    TreasureRepo.getInstance().cache(mArea);
                    TreasureRepo.getInstance().addTreasure(treasureList);

                    mMapFragmentView.setTreasureData(treasureList);
                }
            }

            @Override
            public void onFailure(Call<List<Treasure>> call, Throwable t) {
                mMapFragmentView.showMessage("请求失败"+t.getMessage());
            }
        });
    }
}


