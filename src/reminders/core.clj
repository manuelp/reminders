(ns reminders.core
  (:use [clojure.string :only [split join]])
  (:import [org.joda.time DateTime]
           [java.text SimpleDateFormat]))

(def reminders (ref []))

(defn add [desc when]
  (letfn [(reverse-date [date]
            (join \- (reverse (split date #"-"))))]
    (dosync
     (alter reminders conj [(new DateTime (reverse-date when)) desc]))))

(defn filter-near [days]
  (letfn [(near? [date days]
            (if (. (new DateTime) isAfter (. date minusDays days))
              true
              false))]
    (sort (filter #(near? (first %) days) @reminders))))

(defn format-date-time [date]
  (. (new SimpleDateFormat "dd/MM/yyyy") format (. date toDate)))

(defn format-reminders [reminders]
  (letfn [(format-reminder [reminder]
            (str (format-date-time (first reminder)) " - " (first (rest reminder))))]
    (join \newline (map format-reminder reminders))))

(defn show-me
  ([] (println (format-reminders @reminders)))
  ([days] (println (format-reminders (filter-near days)))))

;; ---- Usage
;;(add "Read SICP" "22-08-2012")
;;(show-me)
;;(show-me 7)

;; TODO Implement persistence
