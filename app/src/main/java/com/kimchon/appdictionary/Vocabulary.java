package com.kimchon.appdictionary;

public class Vocabulary {
    private String word;
    private String content;

    public Vocabulary(String word, String content) {
        this.word = word;
        this.content = content;
    }

    public Vocabulary() {
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
