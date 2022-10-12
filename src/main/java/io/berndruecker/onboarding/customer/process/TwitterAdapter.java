package io.berndruecker.onboarding.customer.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.VariablesAsType;
import io.camunda.zeebe.spring.client.annotation.ZeebeVariablesAsType;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterAdapter {

    @Value("${twitterConsumerKey}")
    private String consumerKey;
    @Value("${twitterConsumerSecret}")
    private String consumerSecret;
    @Value("${twitterAccessToken}")
    private String accessToken;
    @Value("${twitterAccessTokenSecret}")
    private String accessTokenSecret;

    @JobWorker(type="twitter", autoComplete = true)
    public Status publishTweet(@VariablesAsType TwitterInput input) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        String content = input.getTweetContent();
        System.out.println("Tweeting: " + content);
        Status status = twitter.updateStatus(content);
        return status;
    }
}




/*
        String consumerKey = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterConsumerKey");
        String consumerSecret = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterConsumerSecret");
        String accessToken = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterAccessToken");
        String accessTokenSecret = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterAccessTokenSecret");

 */














/*
"appliesTo": [
    "bpmn:Task"
  ],
  "elementType": {
    "value": "bpmn:ServiceTask"
  },
  "properties": [
    {
      "type": "Hidden",
      "value": "twitter",
      "binding": {
        "type": "zeebe:taskDefinition:type"
      }
    },
    {
      "label": "Tweet Content",
      "type": "String",
      "binding": {
        "type": "zeebe:input",
        "name": "tweetContent"
      }
    }
  ]
 */