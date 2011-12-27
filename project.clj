(defproject pinger "0.1.0-SNAPSHOT"
  :description "A Clojure and Redis based uptime tracker"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [log4j "1.2.16"]
                 [javax.mail/mail "1.4.1"]
                 [noir "1.2.1"]
                 [accession "0.1.1"]]
  :main pinger.server)
