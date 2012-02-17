(ns reminders.core
  (:import [org.joda.time DateTime]))

(defn add [desc when reminders]
  (conj reminders [(new DateTime when) desc]))

(defn filter-near [days reminders]
  (letfn [(near? [date days]
            (if (. (new DateTime) isAfter (. date minusDays days))
              true
              false))]
    (sort (filter #(near? (first %) days) reminders))))

;; TODO Use atoms to make this functions easier to use through a REPL
