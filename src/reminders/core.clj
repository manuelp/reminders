(ns reminders.core
  (:use [clojure.string :only [split join]])
  (:import [org.joda.time DateTime]))

(defn add [desc when reminders]
  (letfn [(reverse-date [date]
            (join \- (reverse (split date #"-"))))]
    (conj reminders [(new DateTime (reverse-date when)) desc])))

(defn filter-near [days reminders]
  (letfn [(near? [date days]
            (if (. (new DateTime) isAfter (. date minusDays days))
              true
              false))]
    (sort (filter #(near? (first %) days) reminders))))

;; TODO Use atoms to make this functions easier to use through a REPL
;; TODO Implement persistence
