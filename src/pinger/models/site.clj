(ns pinger.models.site
  (:require [accession.core :as redis]
            [pinger.models.connection :as connection])
  (:import (java.util Date)))

(def ^:dynamic *connection* (redis/connection-map {}))

(defn watch-list []
  (redis/with-connection
    *connection*
    (redis/smembers "sites")))

(defn create [site]
  (redis/with-connection
    *connection*
    (redis/sadd "sites" site)))

(defn current [site]
  (if-let [s (redis/with-connection
               *connection*
               (redis/get site))]
    (let [{:keys [status date]} (read-string s)]
      {:status status
       :date date
       :class (cond
               (nil? status) nil
               (= 200 status) "success"
               (< 200 status) "important"
               :else nil)})
    {:class nil :status "Pending" :date nil}))

(defn check []
  (doseq [site (watch-list)]
    (let [status {:status (connection/response-code site) :date (str (Date.))}]
      (redis/with-connection
        *connection*
        (redis/set site (pr-str status))
        (redis/sadd (str site ":history") (pr-str status))))))