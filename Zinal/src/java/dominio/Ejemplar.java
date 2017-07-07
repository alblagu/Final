package dominio;

/**
 *
 * @author alberto
 */
public class Ejemplar {

	private final String codigo;
	private final Libro libro;
	private final boolean disponible;
	private final String localizacion;

	public Ejemplar(String codigo, Libro libro, boolean disponible, String localizacion) {
		this.codigo=codigo;
		this.libro=libro;
		this.disponible=disponible;
		this.localizacion=localizacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public Libro getLibro() {
		return libro;
	}

	public boolean getDisponible(){
		return disponible;
	}	
	
	public String getLocalizacion(){
		return localizacion;
	}
}