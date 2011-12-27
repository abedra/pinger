(ns pinger.server
  (:require [noir.server :as server]
            [pinger.models.scheduler :as scheduler]
            [pinger.models.site :as site]))

(server/load-views "src/pinger/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (scheduler/periodically (scheduler/scheduled-executor 1)
                            site/check
                            :initial-delay 0
                            :delay (* 60 1000))
    (server/start port {:mode mode
                        :ns 'pinger})))

