package villagegaulois;

import personnages.Chef;

import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche ;

	public Village(String nom, int nbVillageoisMaximum , int nbEtals ) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i=0 ; i<nbEtals ; i++) {
				this.etals[i] = new Etal() ;
			}

		}

		private void utiliserEtals(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if ((indiceEtal < 0) && (indiceEtal < etals.length)) {
				throw new IllegalArgumentException("L'indice de l'étal n'est pas valide");
			}

			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			
			int nb = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()&& etals[i].contientProduit(produit)) {
					nb++;
				}
			}
			int j = 0 ;
			Etal[] etalsProduit =  new Etal[nb];
			for (int i=0 ;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()&& etals[i].contientProduit(produit)) {
					etalsProduit[j] = etals[i];
					j++ ;
				}
				

			}
			
			return etalsProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			int v = 0 ; 
			for (int i=0 ; i<etals.length;i++) {
				if (etals[i].getVendeur()== gaulois) {
					v = 1 ;
					return etals[i];
				}
			}
			
			return null ;
		}
		
		private String afficherMarche() {
			int etalsVide = 0 ;
			StringBuilder chaine = new StringBuilder();
			for(int i=0 ; i<etals.length ; i++) {
				if (etals[i].isEtalOccupe()) {
					//chaine.append(etals[i].getVendeur() + " vend " + etals[i].quantite + " " + etals[i].produit);
					chaine.append(etals[i].afficherEtal());
				}
				else {
					etalsVide ++ ;
				}
			}
			if (etalsVide != 0) {
				chaine.append("Il reste " + etalsVide + " étals non utilisés dans le marché.");
			}
			return chaine.toString();
		}
		
		
	}

	
	public String installerVendeur (Gaulois vendeur , String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur + " chercher un endroit pour vendre " +  nbProduit + " " + produit +".\n");
		Etal etals = new Etal() ; 
		int etalLibre ;
		etalLibre =  marche.trouverEtalLibre();
		if (etalLibre != -1) {
			marche.utiliserEtals(etalLibre,vendeur,produit,nbProduit);
		}
		chaine.append("Le vendeur " + vendeur + " vend des " + produit + " à l'étal n°" + etalLibre);
		return chaine.toString() ;
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if (etals.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marché.\n");
		}
		if (etals.length == 1) {
			chaine.append("Seul le vendeur " + etals[0].getVendeur() + " propose des fleurs au marché.\n");
		}
		else {
			chaine.append("Les venderus qui proposent des " + produit + " sont :\n");
			for (int i=0 ; i<etals.length; i++) {
				chaine.append("- " + etals[i].getVendeur());
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		return etal ; 
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder() ; 
		Etal etal = rechercherEtal(vendeur);
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder(); 
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n");
		return marche.afficherMarche();
		

	}
	
	
	
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException("Village sans chef.");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	
}