package com.a2k.languagespeedup;

public class SentencePair {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private String foreign;
    private String nativeS;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public SentencePair(final String foreign, final String nativeS) {
        this.foreign = foreign;
        this.nativeS = nativeS;
    }

    public String getForeign() {
        return foreign;
    }

    public String getNative() {
        return nativeS;
    }
}
