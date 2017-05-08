package com.proyecto.tfg.superrunningnews.models;

public class Chat {

    private boolean mio;
    private String txt;

    public Chat(String txt, boolean mio) {
        this.mio = mio;
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isMio() {
        return mio;
    }

    public void setMio(boolean mio) {
        this.mio = mio;
    }

}
