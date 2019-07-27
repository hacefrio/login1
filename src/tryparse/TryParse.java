
package tryparse;

/**
 *
 * @author Sebastian
 */
public class TryParse {
    
public boolean TryInt(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}
public boolean TryDouble(String cadena){
	try {
		Double.parseDouble(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}
}