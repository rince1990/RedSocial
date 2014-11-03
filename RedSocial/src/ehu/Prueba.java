package ehu;

public class Prueba {
	
	public static void main(String [ ] args){

		RedSocial facebook = new RedSocial();
		PersonID id = new PersonID ("Erik", "Rodrigues");
		PersonID id2 = new PersonID ("Ainhoa", "Havelka");
		PersonID id3 = new PersonID ("Igor", "Santesteban");
		PersonID id4 = new PersonID ("Adrian", "Gonzalez");
		PersonID id5 = new PersonID ("Iker", "Moya");
		PersonID id6 = new PersonID ("Gontzal", "Ruiz");
		Date fechaNacimiento = new Date(31,4,1999);
		Date fechaNacimiento2 = new Date(5,12,1977);
		Date fechaNacimiento3 = new Date(19,3,1995);
		Date fechaNacimiento4 = new Date(14,7,1910);
		Date fechaNacimiento5 = new Date(21,1,2014);
		Date fechaNacimiento6 = new Date(27,2,1927);
		DatosPersona datos1 = new DatosPersona("Erik", id, fechaNacimiento, "Tarragona");
		DatosPersona datos2 = new DatosPersona("Ainhoa", id2, fechaNacimiento2, "Honolulu");
		DatosPersona datos3 = new DatosPersona("Igor", id3, fechaNacimiento3, "Australia");
		DatosPersona datos4 = new DatosPersona("Adrian", id4, fechaNacimiento4, "Honolulu");
		DatosPersona datos5 = new DatosPersona("Iker", id5, fechaNacimiento5, "Tarragona");
		DatosPersona datos6 = new DatosPersona("Gontzal", id6, fechaNacimiento6, "America");
		String[] titulos1 = {"hawai", "bombai"};
		String[] titulos2 = {"automatas"};
		String[] titulos3 = {"unapeli", "otrapeli", "otrapelimas"};
		String[] titulos4 = {"los juegos del hambre", "harry potter"};
		String[] titulos5 = {"nosequeponer", "nosequemasponer"};
		String[] titulos6 = {"unapelimala"};

		facebook.registrar(datos1.getAlias(), datos1, titulos1);
		facebook.registrar(datos2.getAlias(), datos2, titulos2);
		facebook.registrar(datos3.getAlias(), datos3, titulos3);
		facebook.registrar(datos4.getAlias(), datos4, titulos4);
		facebook.registrar(datos5.getAlias(), datos5, titulos5);
		facebook.registrar(datos6.getAlias(), datos6, titulos6);
		

		System.out.println("SIN ORDENAR");
		facebook.imprimirParticipantes();
		facebook.optimizar();
		System.out.println();
		System.out.println("ORDENADO");
		facebook.imprimirParticipantes();

		System.out.println();
		System.out.println("AÑADIR AMIGOS");
		facebook.registrarAmistad("Iker", "Adrian");
		facebook.registrarAmistad("Igor", "Ainhoa");
		facebook.registrarAmistad("Adrian", "Erik");
		facebook.registrarAmistad("Erik", "Gontzal");
		facebook.registrarSeguidor("Iker", "Adrian");
		facebook.registrarSeguidor("Igor", "Ainhoa");
		facebook.registrarSeguidor("Adrian", "Erik");
		facebook.registrarSeguidor("Erik", "Gontzal");
		
		facebook.optimizar();
		facebook.imprimirParticipantes();
		System.out.println();
		
		System.out.println(facebook.datosPersona("Erik").toString());
		System.out.println();
		
		DatosPersona[] residentes = facebook.residentes("Tarragona", "Moya", "Alb");
		
		System.out.println(residentes[0]);

	}
}


