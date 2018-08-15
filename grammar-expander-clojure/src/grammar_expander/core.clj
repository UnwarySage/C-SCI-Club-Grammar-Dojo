(ns grammar-expander.core
  (:require [cheshire.core :as json]))

(defn load-grammar "loads and validates a grammar, given a filepath."
  [path-to-grammar]
  (let
    [loaded-grammar (json/parse-stream (clojure.java.io/reader path-to-grammar))]
    (do
      (assert (map? loaded-grammar))
      (assert (get loaded-grammar "startpoint"))
      (map #(assert (string? %)) (flatten (reduce conj loaded-grammar))) ;checks to see if every element is a string
      loaded-grammar)))
      ;;some code to check that every replaceent symbols has a corresponding key)
      ;;TODO include a spec implementation

(defn get-substitution "selects a random replacement of the given symbol"
  [grammar rep-symbol]
  (let [options (get grammar rep-symbol)]
    (get options (rand-int (count options)))))

(defn make-substitution
  "replaces the first replacement symbol in the given string and returns the new string, given a grammar."
  [grammar inp-str]
  (let [results (re-find #"(.*)\#(.+)\#(.*)" inp-str)]
  (if results
    (str (nth results 1)
         (get-substitution grammar (nth results 2))
         (nth results 3))
    inp-str)))

(defn expand-string "takes a string and expands it completely, returning the result as a string"
  [grammar inp-str]
  (loop [present-str inp-str
         new-str (make-substitution grammar present-str)]
        (if (= present-str new-str)
            present-str
            (recur new-str (make-substitution grammar new-str)))))

(defn trace-grammar "returns a trace from one of the grammars startpoints as a string."
  [grammar]
  (expand-string grammar (get-substitution grammar "startpoint")))

(defn -main [& args]
  (println (trace-grammar (load-grammar (first args)))))
