package com.github.diniodinev.spellcheck

import groovyx.net.http.*


class Check {
    def urlPath

    public Check() {
    }

    def getHtml(urlPath) {
        def http = new HTTPBuilder(urlPath)
        return http.get([:])
    }

    def printContent(def html) {
        html.each {
            println '-----------' + it
        }
    }

    Boolean checkEmptyResult(def urlPath) {
        boolean isEmpty = false;
        def find = getHtml(urlPath).'**'.find {
            if (it.toString().contains("couldn't find any code matching")) {
                isEmpty = true
                return
            }
        }
        return isEmpty
    }

    String urlCreator(String keyWord) {
        return "https://github.com/bmuschko/gradle-in-action-source/search?utf8=%E2%9C%93&q=${keyWord}&type=Code"
        //return "https://github.com/gradle/gradle/search?q=${keyWord}&ref=cmdform"
    }

    /**
     * Constructs urls for github searching fro given words
     * @param wordsForSearching list of the words which will be search for
     * @return urls for searching particular words
     */
    List<String> urls(List<String> wordsForSearching) {
        def urls = []
        wordsForSearching.each {
            urls.add(urlCreator(it))
        }
    }

    Map<String, Boolean> isPresent(List<String> wordsForSearching) {
        Map<String, Boolean> results = [:]
        wordsForSearching.each { String word ->
            Thread.sleep(5000)
            try {
                results.put(word, !checkEmptyResult(urlCreator(word)))
                println word +" "+ !checkEmptyResult(urlCreator(word))
            }catch(Exception e){
                Thread.sleep(10000)
                results.put(word, !checkEmptyResult(urlCreator(word)))
                println "Errorrrrrrrrrrrrrr ="+word +" "+ !checkEmptyResult(urlCreator(word))
            }
        }
        results
    }
}
