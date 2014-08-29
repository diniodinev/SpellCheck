package com.github.diniodinev.spellcheck

class TestSpelling {
    public static void main(String[] args) {
        List<String> words= [];
        File wrongWords = new File('words-wrong.txt')
        wrongWords.eachLine{
            words.add(it.trim())
        }

        Check page = new Check()
        Map<String, Boolean> results = page.isPresent(words);
        results.each { word, isPresent ->
            println "Is word '$word' presents in the files: $isPresent"
        }

    }
}
