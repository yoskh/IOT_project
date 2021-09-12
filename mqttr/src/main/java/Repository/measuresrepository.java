package Repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import application.mqttr.model.Mesure;


public interface measuresrepository extends ElasticsearchRepository<Mesure, String>{

}
