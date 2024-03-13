package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main (String [] args) {
		Gaulois asterix = new Gaulois("Asterix",5) ;
		Etal etal= new Etal(); 
		etal.libererEtal();
		System.out.println("Fin du test 1");

		try {
			etal.acheterProduit(0, asterix);
			System.out.println("Fin du test 2");

		}catch (IllegalArgumentException e) {
			System.out.println("Quantité de produit négatif");
		}
		try {
			etal.acheterProduit(1,asterix);
		}catch (IllegalArgumentException e) {
			System.out.println("Etal pas occupé");
		}
		
	}
}
