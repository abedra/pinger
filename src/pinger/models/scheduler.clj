(ns pinger.models.scheduler
  (:import (java.util.concurrent ThreadPoolExecutor ScheduledThreadPoolExecutor TimeUnit)))

(defn scheduled-executor ^ScheduledThreadPoolExecutor [threads]
  (ScheduledThreadPoolExecutor. threads))

(defn periodically ^ScheduledFuture [e f & {:keys [initial-delay delay]}]
  (.scheduleWithFixedDelay
   e f
   initial-delay delay
   TimeUnit/MILLISECONDS))

(defn shutdown-executor [^ThreadPoolExecutor e]
  (.shitdown e))