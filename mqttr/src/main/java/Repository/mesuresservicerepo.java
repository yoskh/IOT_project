package Repository;

import org.springframework.stereotype.Service;

import application.mqttr.model.Mesure;

@Service
public class mesuresservicerepo {
	
	private measuresrepository mesrepo;
	
	public void createmeasure( final Mesure mesure) {
	    mesrepo.save(mesure);
	  }
}
