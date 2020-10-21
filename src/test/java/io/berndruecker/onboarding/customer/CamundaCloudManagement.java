package io.berndruecker.onboarding.customer;

import io.zeebe.clustertestbench.cloud.CloudAPIClient;
import io.zeebe.clustertestbench.cloud.request.CreateClusterRequest;
import io.zeebe.clustertestbench.cloud.response.ClusterInfo;
import io.zeebe.clustertestbench.cloud.response.ClusterStatus;
import io.zeebe.clustertestbench.cloud.response.CreateClusterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CamundaCloudManagement {

    /**
     * PEter Ihme created a Java client on top of http://docs.camunda.io/docs/reference/cloud-console-api/cloud-console-api-reference
     * which I use here
     */
    @Autowired
    private CloudAPIClient cloudApi;

    private String currentClusterId;

    /**
     * Takes around 2 minutes
     * @return
     */
    public String createCluster() {
        // ClusterInfo [planType=ClusterPlanTypeInfo [internal=false, name=Development - XS, uuid=f66ccc99-d610-4df0-83ab-595e30b84c5d], k8sContext=K8sContextInfo [uuid=deadbeef-eaea-4bd3-972a-70203f150d88, name=Europe West 1D, region=Europe West 1, zone=D], uuid=1bdf9ffa-d156-4242-98fd-9483024d8fb6, ownerId=c27dd420-d151-492f-8cea-b5482bfaa133, name=test, internal=true, generation=GenerationInfo [name=Tasklist 0.24.0-alpha3, uuid=a6d0500c-fbdf-422f-9f5e-723bce6acc77, versions={zeebe=camunda/zeebe:0.24.2, operate=camunda/operate:0.24.2, tasklist=camunda/zeebe-tasklist:0.24.0-alpha3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], channel=ChannelInfo [allowedGenerations=null, defaultGenerations=null, isDefault=false, name=Internal Dev, uuid=cf355219-cc2d-4266-83e1-2430998ddf30], status=ClusterStatus [operateStatus=Healthy, operateUrl=https://1bdf9ffa-d156-4242-98fd-9483024d8fb6.operate.ultrawombat.com, ready=Healthy, zeebeStatus=Healthy, zeebeUrl=grpcs://1bdf9ffa-d156-4242-98fd-9483024d8fb6.zeebe.ultrawombat.com], metadata=ClusterMetadata [creationTimestamp=null, generation=0, name=1bdf9ffa-d156-4242-98fd-9483024d8fb6, resourceVersion=null, selfLink=null, uid=null]]
//		System.out.println(cloudManagement.getClusterInfo("1bdf9ffa-d156-4242-98fd-9483024d8fb6"));
        System.out.print("POSSIBLE CLOUD PARAMETERS: ");
        System.out.println(cloudApi.getParameters());

        // ParmetersResponse [channels=[ChannelInfo [allowedGenerations=[GenerationInfo [name=Tasklist 0.24.0-alpha3, uuid=a6d0500c-fbdf-422f-9f5e-723bce6acc77, versions={zeebe=camunda/zeebe:0.24.2, operate=camunda/operate:0.24.2, tasklist=camunda/zeebe-tasklist:0.24.0-alpha3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], GenerationInfo [name=Zeebe 0.23.6, uuid=84336cc0-1847-46bd-a567-aa6790dc5072, versions={zeebe=camunda/zeebe:0.23.6, operate=camunda/operate:0.23.2, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.8}], GenerationInfo [name=Migration User Test, uuid=c8c75b63-d4ea-4f53-a4bf-9c82efc493f7, versions={zeebe=camunda/zeebe:0.24.0-alpha2, operate=camunda/operate:0.24.0-version-token-migration-ui2-SNAPSHOT, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], GenerationInfo [name=Zeebe 0.23.4, uuid=6687e149-cb4a-415a-b876-a73b6da069f5, versions={zeebe=camunda/zeebe:0.23.4, operate=camunda/operate:0.23.2, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.8}], GenerationInfo [name=Zeebe 0.23.7, uuid=4a0b818a-2c13-4565-8d0c-09a19e335d4e, versions={zeebe=camunda/zeebe:0.23.7, operate=camunda/operate:0.23.2, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.8}], GenerationInfo [name=Zeebe 0.24.4, uuid=15defc81-42fd-45e9-a1ba-8774be55b1de, versions={zeebe=camunda/zeebe:0.24.4, operate=camunda/operate:0.24.3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], GenerationInfo [name=Operate 0.25.0-rc1 release test, uuid=051add1a-72d6-4bb1-b2bb-cacc1d15a5c1, versions={zeebe=camunda/zeebe:0.25.0-rc1, operate=camunda/operate:0.25.0-rc1, tasklist=camunda/zeebe-tasklist:0.25.0-rc1, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.12}], GenerationInfo [name=Zeebe SNAPSHOT, uuid=2f903c16-9470-4cb9-a7d4-35a88304281e, versions={zeebe=camunda/zeebe:SNAPSHOT, operate=camunda/operate:SNAPSHOT, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.6, zeebeHttpWorker=camunda/zeebe-http-worker:0.22.0}]], defaultGenerations=GenerationInfo [name=Zeebe 0.24.4, uuid=15defc81-42fd-45e9-a1ba-8774be55b1de, versions={zeebe=camunda/zeebe:0.24.4, operate=camunda/operate:0.24.3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], isDefault=false, name=Internal Dev, uuid=cf355219-cc2d-4266-83e1-2430998ddf30], ChannelInfo [allowedGenerations=[GenerationInfo [name=Zeebe 0.23.6, uuid=84336cc0-1847-46bd-a567-aa6790dc5072, versions={zeebe=camunda/zeebe:0.23.6, operate=camunda/operate:0.23.2, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.8}], GenerationInfo [name=Zeebe 0.23.7, uuid=4a0b818a-2c13-4565-8d0c-09a19e335d4e, versions={zeebe=camunda/zeebe:0.23.7, operate=camunda/operate:0.23.2, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.8}], GenerationInfo [name=Zeebe 0.24.4, uuid=15defc81-42fd-45e9-a1ba-8774be55b1de, versions={zeebe=camunda/zeebe:0.24.4, operate=camunda/operate:0.24.3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}]], defaultGenerations=GenerationInfo [name=Zeebe 0.24.4, uuid=15defc81-42fd-45e9-a1ba-8774be55b1de, versions={zeebe=camunda/zeebe:0.24.4, operate=camunda/operate:0.24.3, elasticSearchCurator=quay.io/pires/docker-elasticsearch-curator:5.5.1, elasticSearchOss=docker.elastic.co/elasticsearch/elasticsearch-oss:6.8.10}], isDefault=true, name=Free Beta, uuid=6bdf0d1c-3d5a-4df6-8d03-762682964d85]], clusterPlanTypes=[ClusterPlanTypeInfo [internal=false, name=Development with big ElasticSearch, uuid=1391a622-4ab3-40e9-abcd-0bccde795911], ClusterPlanTypeInfo [internal=false, name=Development - XS, uuid=f66ccc99-d610-4df0-83ab-595e30b84c5d], ClusterPlanTypeInfo [internal=true, name=Production - S Tasklist, uuid=5a9c664c-c257-47c2-b2d8-cadd6330dd6f], ClusterPlanTypeInfo [internal=true, name=Development - NP, uuid=4668cf6f-1635-4dde-9b57-6bbac72af98c], ClusterPlanTypeInfo [internal=false, name=Test - S, uuid=6fe8efda-3297-4afa-ab4a-7735cf750f65], ClusterPlanTypeInfo [internal=false, name=Production - M, uuid=38a82af9-7a55-4003-a6af-2948fbde93e3], ClusterPlanTypeInfo [internal=false, name=Production - S, uuid=736d9100-0155-4af5-be14-b09c42de8417], ClusterPlanTypeInfo [internal=false, name=Production - L, uuid=6ddfe2a1-c206-4ba0-8c11-583d459a6645], ClusterPlanTypeInfo [internal=false, name=Development, uuid=7b6494ee-eb55-4e0c-8ab8-5f9e64be146d], ClusterPlanTypeInfo [internal=false, name=Development - XS, uuid=305aea82-8366-4254-9466-2436f8bcefde], ClusterPlanTypeInfo [internal=false, name=Test - S, uuid=c1b62768-6a8d-4475-a158-78e52d22b77e], ClusterPlanTypeInfo [internal=false, name=Production - S, uuid=2563c475-447b-4458-9453-2df12c7b0d5b], ClusterPlanTypeInfo [internal=false, name=Production - M, uuid=0282d9dd-266f-4db6-a451-724ff62648f4], ClusterPlanTypeInfo [internal=false, name=Production - L, uuid=e4110e75-5bef-4344-963d-5eb144b33fe7], ClusterPlanTypeInfo [internal=false, name=Development, uuid=5fb4ef44-0f6b-4677-97a2-a91f6053b86b]],
        // regions=[RegionInfo [name=Europe West 1D, region=Europe West 1, uuid=deadbeef-eaea-4bd3-972a-70203f150d88, zone=D], RegionInfo [name=new chaos, region=Europe West 1, uuid=d4f0052d-430d-484d-b892-39a303d28079, zone=D]]]

        String planTypeId = "f66ccc99-d610-4df0-83ab-595e30b84c5d";
        String channelId = "cf355219-cc2d-4266-83e1-2430998ddf30";
        String generationId = "a6d0500c-fbdf-422f-9f5e-723bce6acc77";
        String regionId = "deadbeef-eaea-4bd3-972a-70203f150d88";

        System.out.print("Create new cluster");
        CreateClusterResponse response = cloudApi.createCluster(new CreateClusterRequest("JUnit Test Case [automatically created]", planTypeId, channelId, generationId, regionId));

        currentClusterId = response.getClusterId();
        String clusterStatus = null;

        System.out.println("Waiting for cluster " + currentClusterId + " to become available");
        while (clusterStatus==null || "Unknown".equals(clusterStatus) || "Unhealthy".equals(clusterStatus)) {
            System.out.print(".");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread sleep was interrupted", e);
            }
            clusterStatus = Optional.ofNullable(cloudApi.getClusterInfo(currentClusterId))
                    .map(ClusterInfo::getStatus).map(ClusterStatus::getReady).orElse("Unknown");
        }
        System.out.println("\nCluster now in status: " + clusterStatus);
        return currentClusterId;
    }

    public void deleteCurrentCluster() {
        cloudApi.deleteCluster(currentClusterId);
    }
}
