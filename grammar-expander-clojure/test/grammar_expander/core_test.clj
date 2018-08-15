(ns grammar-expander.core-test
  (:require [expectations :refer :all]
            [grammar-expander.core :refer :all]))

(def simple-grammar {"startpoint" ["option: #option#"]
                     "option" ["A" "B"]})

(def two-layer-grammar {"startpoint" ["#person#"]
                      "person" ["#name# the #title#"]
                      "name" ["Vincent"]
                      "title" ["third"]})

(def linear-grammar {"startpoint" ["#creature# appears!"]
                      "creature" ["#species#"]
                      "species" ["#mouse#"]
                      "mouse" ["Martin"]})
;;tests for load-grammar will be written once I decide where the default grammars are stored.

;;get-substitution
(expect "Vincent" (get-substitution two-layer-grammar "name"))
(expect #(contains? #{"A" "B"} %) (get-substitution simple-grammar "option"))

;;make-substitution
(expect "switch at the beginning" (make-substitution {"thing" ["switch"]} "#thing# at the beginning"))
(expect "A switch in the middle" (make-substitution {"thing" ["switch"]} "A #thing# in the middle"))
(expect "And at the end, a switch" (make-substitution {"thing" ["switch"]} "And at the end, a #thing#"))
(expect "No switch at all" (make-substitution {"thing" ["switch"]} "No switch at all"))

;;expand-string
(expect "Martin" (expand-string linear-grammar "#creature#"))
(expect "Vincent the third" (expand-string two-layer-grammar "#person#"))
(expect "No change at all" (expand-string two-layer-grammar "No change at all"))

;;trace-grammar
(expect "Vincent the third" (trace-grammar two-layer-grammar))
(expect "Martin appears!" (trace-grammar linear-grammar))
(expect  #(contains? #{"option: A" "option: B"} %) (trace-grammar simple-grammar))
