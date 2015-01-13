package com.ofg.twitter.controller.place.extractor

import com.ofg.twitter.controller.place.model.Tweet

interface PropagationWorker {
    void collectAndPropagate(long pairId, List<Tweet> tweets)
}