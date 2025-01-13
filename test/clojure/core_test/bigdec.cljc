(ns clojure.core-test.bigdec
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability :as p]))

(p/when-var-exists clojure.core/bigdec
  (deftest test-bigdec
    (are [expected x] (= expected (bigdec x))
      1M    1
      0M    0
      -1M   -1
      1M    1N
      0M    0N
      -1M   -1N
      1M    1.0
      0M    0.0
      -1M   -1.0
      0.5M  1/2
      0M    0/2
      -0.5M -1/2)

    ;; `bigdec` must produce objects that satisfy `decimal?`
    (is (decimal? (bigdec 1)))

    #?@(:cljs nil
        :default
        [(is (instance? java.math.BigDecimal (bigdec 1)))])))
