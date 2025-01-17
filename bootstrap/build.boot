(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.3" :scope "test"]
                  [cljsjs/jquery "3.4.0-0"] ])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.4.1")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom  {:project     'cljsjs/bootstrap
        :version     +version+
        :description "Javascript for Bootstap"
        :url         "http://getbootstrap.com"
        :license     {"MIT" "http://opensource.org/licenses/MIT"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
   (download :url (str "https://github.com/twbs/bootstrap/releases/download/v" +lib-version+ "/bootstrap-" +lib-version+ "-dist.zip")
             :checksum "b40f2ec8769ebee23a395fa2b907e4b4"
             :unzip true)
   (sift :move {#"^bootstrap-([\d\.]*)-dist/js/bootstrap.js" "cljsjs/bootstrap/development/bootstrap.inc.js"
                #"^bootstrap-([\d\.]*)-dist/js/bootstrap.min.js" "cljsjs/bootstrap/production/bootstrap.min.inc.js"})
   (deps-cljs :name "cljsjs.bootstrap" :requires ["cljsjs.jquery"])
   (sift :include #{#"^cljsjs" #"^deps\.cljs$"})
   (pom)
   (jar)))
