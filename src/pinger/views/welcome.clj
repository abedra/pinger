(ns pinger.views.welcome
  (:use [noir.core :only [defpage defpartial render]])
  (:require [noir.session :as session]
            [noir.response :as response]
            [noir.validation :as validation]
            [hiccup.page-helpers :as page]
            [hiccup.form-helpers :as form]
            [pinger.views.common :as common]
            [pinger.models.site :as site]))

(defpartial print-error [[err]]
  [:p {:class "alert-message error"} err])

(defpartial tracker-form []
  [:div {:style "padding-top: 20px;"}
   (form/form-to [:post "/"]
                 (validation/on-error :site print-error)
                 (form/text-field {:class "span5 defaultText"
                                   :style "margin-right: 5px;"
                                   :data "Enter a URL or IP Address to track"}
                                  "site")
                 (form/submit-button "Track"))])

(defpartial watch-list []
  [:table {:id "watch-list" :class "condensed-table"}
   [:thead
    [:tr
     [:th "Site/IP Address"]
     [:th "Current Status"]
     [:th "Last Updated"]]]
   [:tbody
    (map (fn [s]
           (let [{:keys [status date class]} (site/current s)]
             [:tr
              [:td s]
              [:td [:span {:class (str "label " class)} status]]
              [:td date]]))
         (site/watch-list))]])

(defpage "/" []
  (common/layout
   [:h2 "Pinger"]
   (if-let [alert (session/flash-get)]
     [:p {:class "alert-message success"} alert])
   (tracker-form)
   (watch-list)))

(defn valid? [url]
  (validation/rule (re-find #"^((https?)://|(www)\.)[a-z0-9-]+(\.[a-z0-9-]+)+([/?].*)?$" url)
                   [:site "The site must be a valid url"]))

(defpage [:post "/"] {:keys [site]}
  (if (valid? site)
    (do
      (site/create site)
      (session/flash-put! (str site " added to to watch list"))
      (response/redirect "/"))
    (render "/")))
