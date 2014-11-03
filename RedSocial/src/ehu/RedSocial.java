package ehu;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RedSocial {

	private List<Participante> participantes;
	private List<Localidad> localidades;
	private List<String[]> amistades;
	private List<String[]> seguimientos;
	private List<String[]> nuevosUsuarios;

	private boolean optimizada;

	public RedSocial() {
		participantes = new ArrayList<Participante>();
		localidades = new ArrayList<Localidad>();
		amistades = new ArrayList<String[]>();
		seguimientos = new ArrayList<String[]>();
		nuevosUsuarios = new ArrayList<String[]>();
	}

	public boolean registrar(String alias, DatosPersona datos, String[] titulosFavoritos) {
		if (datos.getCity() != null) {
			String[] usuario = { alias, datos.getCity() };
			nuevosUsuarios.add(usuario);
		}
		participantes.add(new Participante(alias, datos, titulosFavoritos));
		optimizada = false;
		return true;
	}

	public boolean registrarAmistad(String unAlias, String otroAlias) {
		String[] amistad = {unAlias, otroAlias};
		amistades.add(amistad);
		optimizada = false;
		return true;
	}

	public boolean registrarSeguidor(String seguidor, String lider) {
		String[] seguimiento = {lider, seguidor};
		seguimientos.add(seguimiento);
		optimizada = false;
		return true;
	}

	public void optimizar() {
		if (!optimizada) {
			Collections.sort(participantes, comparadorAlias);
			while (!amistades.isEmpty()) {
				String[] tmp = amistades.remove(amistades.size() - 1);
				int posAmigo1 = Collections.binarySearch(participantes, new Participante(tmp[0]), comparadorAlias);
				int posAmigo2 = Collections.binarySearch(participantes, new Participante(tmp[1]), comparadorAlias);
				participantes.get(posAmigo1).amigos.add(participantes.get(posAmigo2).datos);
				participantes.get(posAmigo2).amigos.add(participantes.get(posAmigo1).datos);
			}
			while (!seguimientos.isEmpty()) {
				String[] tmp = seguimientos.remove(seguimientos.size() - 1);
				int posLider = Collections.binarySearch(participantes, new Participante(tmp[0]), comparadorAlias);
				int posSeguidor = Collections.binarySearch(participantes, new Participante(tmp[1]), comparadorAlias);
				participantes.get(posLider).seguidores.add(participantes.get(posSeguidor).datos);
			}
			while (!nuevosUsuarios.isEmpty()) {
				String[] tmp = nuevosUsuarios.remove(nuevosUsuarios.size() - 1);
				int posUsuario = Collections.binarySearch(participantes, new Participante(tmp[0]), comparadorAlias);
				int posCiudad = Collections.binarySearch(localidades, new Localidad(tmp[1]), comparadorLocalidades);
				if (posCiudad >= 0) {
					localidades.get(posCiudad).residentes.add(participantes.get(posUsuario).datos);
				}
				else {
					localidades.add(new Localidad(tmp[1], participantes.get(posUsuario).datos));
					Collections.sort(localidades, comparadorLocalidades);
				}
			}

		}
	}

	public DatosPersona datosPersona(String alias) {
		if (!optimizada) optimizar();
		int pos = Collections.binarySearch(participantes, new Participante(alias), comparadorAlias);
		if (pos < 0) return null;
		else return participantes.get(pos).datos;
	}

	public String[] favoritos(String alias) {
		if (!optimizada) optimizar();
		int pos = Collections.binarySearch(participantes, new Participante(alias), comparadorAlias);
		if (pos < 0) return null;
		else return participantes.get(pos).titulosFavoritos.clone();
	}

	public DatosPersona[] amigos(String alias) {
		if (!optimizada) optimizar();
		int pos = Collections.binarySearch(participantes, new Participante(alias), comparadorAlias);
		if (pos < 0) return null;
		else {
			Collections.sort(participantes.get(pos).amigos, comparadorDatosPersona);
			return participantes.get(pos).amigos.toArray(
					new DatosPersona[participantes.get(pos).amigos.size()]);
		}
	}

	public DatosPersona[] seguidores(String alias) {
		if (!optimizada)optimizar();
		int pos = Collections.binarySearch(participantes, new Participante(alias), comparadorAlias);
		if (pos < 0) return null;
		else {
			Collections.sort(participantes.get(pos).seguidores, comparadorDatosPersona);
			return participantes.get(pos).seguidores.toArray(
					new DatosPersona[participantes.get(pos).amigos.size()]);
		}
	}

	public DatosPersona[] residentes(String localidad, String a, String b) {
		// Busca la posición de la localidad en la lista
		// y en caso de no encontrarla, devuelve un array vacío
		int posCiudad = Collections.binarySearch(localidades, new Localidad(localidad), comparadorLocalidades);
		if (posCiudad < 0) return new DatosPersona[0];
		List<DatosPersona> residentes = localidades.get(posCiudad).residentes;
		Collections.sort(residentes, comparadorResidente);
		
		// Recorre la lista para encontrar el rango del array a devolver
		int primero = -1, ultimo = -1;
		for (int i = 0; i < residentes.size() && primero < 0; i++) {
			if (residentes.get(i).getId().getLastName().compareTo(a) >= 0 ) primero = i;
		}
		if (primero < 0) primero = residentes.size();
		for (int j = primero; j < residentes.size() && ultimo < 0; j++) {
			if (residentes.get(j).getId().getLastName().compareTo(b) > 0) ultimo = j;
		}
		System.out.println(primero + " " + ultimo);
		if (ultimo < 0) ultimo = residentes.size();
		residentes = residentes.subList(primero, ultimo);
		return residentes.toArray(new DatosPersona[residentes.size()]);
	}

	public void imprimirParticipantes() {
		for (int i = 0; i < participantes.size(); i++) {
			System.out.println(i + ". Alias: " + participantes.get(i).alias);
			System.out.print("   Amigos: ");
			for (int j = 0; j < participantes.get(i).amigos.size(); j++)
				System.out.print(participantes.get(i).amigos.get(j).getAlias() + " ");
			System.out.println();
			System.out.print("   Seguidores: ");
			for (int j = 0; j < participantes.get(i).seguidores.size(); j++)
				System.out.print(participantes.get(i).seguidores.get(j).getAlias() + " ");
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < localidades.size(); i++) {
			System.out.print(localidades.get(i).nombreCiudad + ": ");
			for (int j = 0; j < localidades.get(i).residentes.size(); j++) {
				System.out.print(localidades.get(i).residentes.get(j).getAlias() + " ");
			}
			System.out.println();
		}
	}

	private class Participante {
		String alias;
		DatosPersona datos;
		String[] titulosFavoritos;
		List<DatosPersona> amigos;
		List<DatosPersona> seguidores;

		public Participante(String alias) {
			this.alias = alias;
		}

		public Participante(String alias, DatosPersona datos, String[] favoritos) {
			this.alias = alias;
			this.datos = datos;
			this.titulosFavoritos = favoritos;
			amigos = new ArrayList<DatosPersona>();
			seguidores = new ArrayList<DatosPersona>();
		}	
	}

	private class Localidad {
		String nombreCiudad;
		List<DatosPersona> residentes;

		public Localidad(String nombreCiudad) {
			this.nombreCiudad = nombreCiudad;
			this.residentes = new ArrayList<DatosPersona>();
		}

		public Localidad(String nombreCiudad, DatosPersona datos) {
			this.nombreCiudad = nombreCiudad;
			this.residentes = new ArrayList<DatosPersona>();
			residentes.add(datos);
		}
	}

	static final Comparator<Participante> comparadorAlias = new Comparator<Participante>() {
		public int compare(Participante a, Participante b) {
			return a.alias.compareTo(b.alias);
		}
	};

	static final Comparator<DatosPersona> comparadorDatosPersona = new Comparator<DatosPersona>() {
		public int compare(DatosPersona a, DatosPersona b) {
			return a.getAlias().compareTo(b.getAlias());
		}
	};

	static final Comparator<DatosPersona> comparadorResidente = new Comparator<DatosPersona>() {
		public int compare(DatosPersona a, DatosPersona b) {
			int c = a.getId().compareTo(b.getId());
			if (c == 0) c = a.getBornDate().compareTo(b.getBornDate());
			return c;
		}
	};
	

	static final Comparator<Localidad> comparadorLocalidades = new Comparator<Localidad>() {
		public int compare(Localidad a, Localidad b) {
			return a.nombreCiudad.compareTo(b.nombreCiudad);
		};
	};
	
}
	

	
