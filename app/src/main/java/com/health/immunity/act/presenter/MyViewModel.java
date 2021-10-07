package com.health.immunity.act.presenter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    public MutableLiveData<String> mutableLiveData=new MutableLiveData<>();


    public  MutableLiveData<String> getLiveData(){
        return mutableLiveData;
    }


}
