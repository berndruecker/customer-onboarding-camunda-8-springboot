package io.berndruecker.onboarding.customer.process;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@OutboundConnector(name="TwitterConnector", type = "twitter",
        inputVariables = "tweetContent")
public class TwitterConnector implements OutboundConnectorFunction {

    @Override
    public Object execute(OutboundConnectorContext outboundConnectorContext) throws Exception {
        String consumerKey = outboundConnectorContext.getSecretStore()
                .replaceSecret("secrets.twitterConsumerKey");
        String consumerSecret = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterConsumerSecret");
        String accessToken = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterAccessToken");
        String accessTokenSecret = outboundConnectorContext.getSecretStore().replaceSecret("secrets.twitterAccessTokenSecret");


        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        TwitterInput input = outboundConnectorContext.getVariablesAsType(TwitterInput.class);
        String content = input.getTweetContent();
        System.out.println("Tweeting: " + content);
        Status status = twitter.updateStatus(content);
        return status;
    }

}
