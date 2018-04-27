package com.example.root.dubai.Model;

public class ExploreModel {

    private String TxtTitle, TxtSubTitle;

    public ExploreModel(String _txtTitle, String _txtSubTitle) {
        this.TxtSubTitle = _txtSubTitle;
        this.TxtTitle = _txtTitle;
    }

    public ExploreModel() {}

    public String getTxtTitle() {
        return TxtTitle;
    }

    public String getTxtSubTitle() {
        return TxtSubTitle;
    }
}
