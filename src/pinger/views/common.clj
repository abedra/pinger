(ns pinger.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css include-js html5]]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "pinger"]
    (include-css "/css/reset.css"
                 "/css/bootstrap.min.css"
                 "/css/application.css")
    (include-js "/js/jquery.js"
                "/js/jquery.form-defaults.js"
                "/js/application.js")]
   [:body
    [:div.container
     content]]))
