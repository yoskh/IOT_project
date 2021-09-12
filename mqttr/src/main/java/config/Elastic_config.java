package config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;



@Configuration
@EnableElasticsearchRepositories(basePackages 
        = "io.pratik.elasticsearch.repositories")
@ComponentScan(basePackages = { "io.pratik.elasticsearch" })
public class Elastic_config extends 
         AbstractElasticsearchConfiguration {
  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {
	  RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
	 return(client) ;
  }
		

}
