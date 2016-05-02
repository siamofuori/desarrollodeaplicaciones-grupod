package unq.tpi.desapp.model.inscription;

import unq.tpi.desapp.model.Inscription;

public class InscriptionPending extends InscriptionState {

	public InscriptionPending() {
	}
	
	@Override
	public void accepted(Inscription inscription) {
		inscription.setInscriptionState(new InscriptionAccepted());
	}

}
