package com.rishabh.medidocapp.ChestCancer.MVP;

public class ChestPresenter implements ChestContract.presenter
{
    ChestContract.view mvpview;

    public ChestPresenter(ChestContract.view mvpview) {
        this.mvpview = mvpview;
    }
}
